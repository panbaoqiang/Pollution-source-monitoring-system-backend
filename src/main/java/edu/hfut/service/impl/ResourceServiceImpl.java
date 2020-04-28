package edu.hfut.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.hfut.dao.TkResourceMapper;
import edu.hfut.pojo.dto.ResourceDTO;
import edu.hfut.pojo.entity.Resource;
import edu.hfut.service.IResourceService;
import edu.hfut.util.comon.CommonResponse;
import edu.hfut.util.comon.SnowFlakeUtil;
import edu.hfut.util.comon.StatusCode;
import edu.hfut.util.exception.BussinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-27 23:11
 */
@Service
public class ResourceServiceImpl implements IResourceService {
    @Autowired
    private TkResourceMapper tkResourceMapper;

    @Autowired
    private SnowFlakeUtil snowFlakeUtil;

    @Override
    public CommonResponse getAllResource(ResourceDTO request) {
        Resource resourceEntity = new Resource();
        List<Resource> resources = tkResourceMapper.select(resourceEntity);
        return new CommonResponse(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg(),resources);
    }

    @Override
    public CommonResponse getResourceChildrenByParentId(ResourceDTO request, Integer startPage, Integer pageSize) {
        List<String> childrenIdsByParentId = getChildrenIdsByParentId(request);
        PageHelper.startPage(startPage,pageSize);
        List<Resource> childrenResourcesByIds = tkResourceMapper.queryResourceByIds(childrenIdsByParentId);
        PageInfo<Resource> pageInfo = new PageInfo<Resource>(childrenResourcesByIds);
        return new CommonResponse<>(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg(),pageInfo);
    }

    @Override
    public CommonResponse addResource(ResourceDTO request, String operatorId) {
        request.setBtnPermissionValue(request.getPath().replace("/",""));
        Resource resource = new Resource();
        BeanUtils.copyProperties(request,resource);
        Date currentTime = new Date();
        resource.setCreatedBy(operatorId);
        resource.setUpdatedBy(operatorId);
        resource.setIsRedirect(0);
        resource.setCreatedTime(currentTime);
        resource.setUpdatedTime(currentTime);
        resource.setVersion(1);
        resource.setStatus(1);
        Long nextId = snowFlakeUtil.nextId();
        System.out.println("nextId:"+nextId);
        resource.setId(nextId+"");
        int count = tkResourceMapper.insertSelective(resource);
        if(count == 0){
            throw new BussinessException(StatusCode.ADD_ERR);
        }
        return new CommonResponse<>(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg());
    }

    @Override
    public CommonResponse updateResource(ResourceDTO request, String operateId) {
        Example example = new Example(Resource.class);
        example.createCriteria().andEqualTo("id",request.getId()).andEqualTo("version",request.getVersion());
        Resource resource = new Resource();
        BeanUtils.copyProperties(request,resource);
        resource.setVersion(resource.getVersion()+1);
        resource.setUpdatedBy(operateId);
        resource.setUpdatedTime(new Date());
        int count = tkResourceMapper.updateByExampleSelective(resource, example);
        if(count == 0){
            throw new BussinessException(StatusCode.UPDATE_ERR);
        }
        return new CommonResponse<>(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg());
    }

    @Override
    @Transactional
    public CommonResponse deleteResource(ResourceDTO request) {
        Resource resource = new Resource();
        BeanUtils.copyProperties(request,resource);
        if(request.getId() != null && !request.getId().equals("")){
            tkResourceMapper.deleteByPrimaryKey(resource);
        }else{
            throw new BussinessException(StatusCode.DEL_ERR);
        }
        return new CommonResponse<>(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg());
    }

    @Override
    @Transactional
    public CommonResponse deleteMultipleResource(ResourceDTO request) {
        if(request.getResourceIdList() != null && !request.getResourceIdList().isEmpty()){
            tkResourceMapper.deleteMultpleResource(request.getResourceIdList());;
        }else{
            throw new BussinessException(StatusCode.DEL_ERR);
        }
        return new CommonResponse<>(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg());
    }

    /**
     * 私有函数
     * 获取某parentId下的子集
     * @param resource
     * @return
     */
    private List<String> getChildrenIdsByParentId(ResourceDTO resource) {
        // 如果不传就是0
        if(resource.getId() == null || resource.getId().equals("")){
            resource.setId("0");
        }
        List<String> total = new ArrayList<>();
        total.add(resource.getId());
        Queue<String> queue = new LinkedList<String>();
        queue.offer(resource.getId());
        while(!queue.isEmpty()){
            String first = queue.poll();
            List<String> childrenIdsByParentId = tkResourceMapper.getChildrenIdsByParentId(first);
            queue.addAll(childrenIdsByParentId);
            total.addAll(childrenIdsByParentId);
        }
        return total;
    }
}
