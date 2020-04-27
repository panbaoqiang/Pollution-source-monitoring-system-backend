package edu.hfut.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.hfut.pojo.dto.ResourceDTO;
import edu.hfut.service.IResourceService;
import edu.hfut.util.comon.CommonResponse;
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


    @Override
    public CommonResponse getAllResource(ResourceDTO request) {
        return null;
    }

    @Override
    public CommonResponse getResourceChildrenByParentId(ResourceDTO request, Integer startPage, Integer pageSize) {
        return null;
    }

    @Override
    public CommonResponse addResource(ResourceDTO request, String operatorId) {
        return null;
    }

    @Override
    public CommonResponse updateResource(ResourceDTO request, String operateId) {
        return null;
    }

    @Override
    public CommonResponse deleteResource(ResourceDTO request) {
        return null;
    }

    @Override
    public CommonResponse deleteMultipleResource(ResourceDTO request) {
        return null;
    }
}
