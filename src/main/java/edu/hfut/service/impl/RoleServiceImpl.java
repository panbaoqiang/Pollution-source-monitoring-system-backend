package edu.hfut.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.hfut.dao.TkRoleMapper;
import edu.hfut.dao.TkRoleResourceMapper;
import edu.hfut.dao.TkUserRoleMapper;
import edu.hfut.pojo.dto.RoleDTO;
import edu.hfut.pojo.entity.Role;
import edu.hfut.pojo.entity.RoleResource;
import edu.hfut.pojo.entity.UserRole;
import edu.hfut.service.IRoleService;
import edu.hfut.util.comon.CommonResponse;
import edu.hfut.util.comon.SnowFlakeUtil;
import edu.hfut.util.comon.StatusCode;
import edu.hfut.util.exception.BussinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-27 23:11
 */
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private TkRoleMapper tkRoleMapper;

    @Autowired
    private TkRoleResourceMapper tkRoleResourceMapper;

    @Autowired
    private TkUserRoleMapper tkUserRoleMapper;
    @Autowired
    private SnowFlakeUtil snowFlakeUtil;
    @Override
    public CommonResponse getAllRole(RoleDTO request) {
        List<Role> result = tkRoleMapper.selectAll();
        return new CommonResponse(StatusCode.SUCCEED.getCode(), StatusCode.SUCCEED.getMsg(),result);
    }

    @Override
    public CommonResponse getCurrentPageRole(RoleDTO request, Integer startPage, Integer pageSize) {
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        // 角色名不为空
        if(request.getName() != null && !"".equals(request.getName())){
            System.out.println("角色名不为空");
            criteria.andLike("name", "%"+request.getName()+"%" );
        }
        PageHelper.startPage(startPage,pageSize);
        List<Role> roles = tkRoleMapper.selectByExample(example);
        PageInfo<Role> pageInfo = new PageInfo<Role>(roles);
        return new CommonResponse(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg(),pageInfo);
    }

    @Override
    @Transactional
    public CommonResponse addRole(RoleDTO request, String operatorId) {
        Role roleEntity = new Role();
        BeanUtils.copyProperties(request,roleEntity);
        try{
            Date date = new Date();
            roleEntity.setCreatedBy(operatorId);
            roleEntity.setCreatedTime(date);
            roleEntity.setUpdatedBy(operatorId);
            roleEntity.setUpdatedTime(date);
            roleEntity.setVersion(1);
            long id = snowFlakeUtil.nextId();
            roleEntity.setId(id+"");
            int count = tkRoleMapper.insert(roleEntity);
            if(count == 0){
                throw new BussinessException(StatusCode.ADD_ERR);
            }
        }catch (Exception e){
            throw new BussinessException(StatusCode.ADD_ERR);
        }
        return new CommonResponse(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg());
    }

    @Override
    @Transactional
    public CommonResponse updateRole(RoleDTO request, String operatorId) {
        Role roleEntity = new Role();
        BeanUtils.copyProperties(request,roleEntity);
        try{
            Example example = new Example(Role.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("version", roleEntity.getVersion());
            criteria.andEqualTo("id",roleEntity.getId());
            roleEntity.setUpdatedBy(operatorId);
            roleEntity.setUpdatedTime(new Date());
            roleEntity.setVersion(roleEntity.getVersion()+1);
            int count = tkRoleMapper.updateByExampleSelective(roleEntity,example);
            if(count == 0){
                throw new BussinessException(StatusCode.UPDATE_ERR);
            }
        }catch (Exception e){
            throw new BussinessException(StatusCode.UPDATE_ERR);
        }
        return new CommonResponse(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg());
    }

    @Override
    @Transactional
    public CommonResponse deleteRole(RoleDTO request) {
        if(request.getId() == null || request.getId().equals("")){
            throw new BussinessException(StatusCode.DEL_ERR);
        }
        Role roleEntity = new Role();
        BeanUtils.copyProperties(request,roleEntity);
        try {
            // 删除掉所有有关的user - role信息
            UserRole userRole = new UserRole();
            userRole.setRoleId(request.getId());
            tkUserRoleMapper.delete(userRole);
            // 删除所有有关的role - resource
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleId(request.getId());
            tkRoleResourceMapper.delete(roleResource);
            // 在删除这个role
            tkRoleMapper.deleteByPrimaryKey(roleEntity.getId());
        }catch (Exception e){
            throw new BussinessException(StatusCode.DEL_ERR);
        }
        return new CommonResponse(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg());
    }

    @Override
    @Transactional
    public CommonResponse deleteMultipleRole(RoleDTO request) {
        if(request.getRoleIdList() == null || request.getRoleIdList().isEmpty()){
            throw new BussinessException(StatusCode.DEL_ERR);
        }
        try {
            tkUserRoleMapper.deleteMultipleUserRoleByRoleId(request.getRoleIdList());
            tkRoleResourceMapper.deleteMultipleRoleResourceByRoleId(request.getRoleIdList());
            tkRoleMapper.deleteMultipleRole(request.getRoleIdList());
        }catch (Exception e){
            throw new BussinessException(StatusCode.DEL_ERR);
        }
        return new CommonResponse(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg());
    }

    @Override
    public CommonResponse assignRolesForResources(RoleDTO request) {
        if(request.getRoleIdList() == null || request.getResourceIds() == null || request.getResourceIds().isEmpty() || request.getRoleIdList().isEmpty()){
            throw new BussinessException(StatusCode.ASSIGN_ROLE_RESOURCE_ERR);
        }
        try {
            // 根据roleIdList清空所有关系
            tkRoleMapper.clearRoleResourceByRoleId(request.getRoleIdList());
            //创建roleResource对象
            List<RoleResource> roleResources = createdRoleResource(request);
            // try catch 增加所有的对象
            tkRoleResourceMapper.insertList(roleResources);
        }catch (Exception e){
            throw new BussinessException(StatusCode.ASSIGN_ROLE_RESOURCE_ERR);
        }
        return new CommonResponse(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg());
    }

    @Override
    public CommonResponse assignRolesForUsers(RoleDTO request) {
        if(request.getRoleIdList() == null || request.getUserIds() == null || request.getUserIds().isEmpty() || request.getRoleIdList().isEmpty()){
            throw new BussinessException(StatusCode.ASSIGN_ROLE_USER_ERR);
        }
        try {
            // 根据roleIdList清空所有关系
            tkRoleMapper.clearRoleUserByRoleId(request.getRoleIdList());
            //创建roleResource对象
            List<UserRole> userRoles = createdRoleUser(request);
            // try catch 增加所有的对象
            tkUserRoleMapper.insertList(userRoles);
        }catch (Exception e){
            throw new BussinessException(StatusCode.ASSIGN_ROLE_USER_ERR);
        }
        return new CommonResponse(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg());
    }
    /**
     * 私有函数，创建一个roleResource集合
     * @param request
     * @return
     */
    private List<RoleResource> createdRoleResource(RoleDTO request){
        List<RoleResource> array = new ArrayList<>(request.getRoleIdList().size() * request.getResourceIds().size());
        for( int i = 0 ; i < request.getRoleIdList().size() ; i++){
            for(int j=0 ; j<request.getResourceIds().size() ; j++){
                RoleResource roleResource = new RoleResource();
                roleResource.setRoleId(request.getRoleIdList().get(i));
                roleResource.setResourceId(request.getResourceIds().get(j));
                array.add(roleResource);
            }
        }
        return array;
    }
    /**
     * 私有函数，创建一个userRole集合
     * @param request
     * @return
     */
    private List<UserRole> createdRoleUser(RoleDTO request){
        List<UserRole> array = new ArrayList<>(request.getRoleIdList().size() * request.getUserIds().size());
        for( int i = 0 ; i < request.getRoleIdList().size() ; i++){
            for(int j=0 ; j<request.getUserIds().size() ; j++){
                UserRole userRole = new UserRole();
                userRole.setRoleId(request.getRoleIdList().get(i));
                userRole.setUserId(request.getUserIds().get(j));
                array.add(userRole);
            }
        }
        return array;
    }
}
