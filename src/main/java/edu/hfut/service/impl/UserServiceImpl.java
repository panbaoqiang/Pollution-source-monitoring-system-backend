package edu.hfut.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.hfut.dao.TkUserMapper;
import edu.hfut.dao.TkUserRoleMapper;
import edu.hfut.pojo.dto.RoleDTO;
import edu.hfut.pojo.dto.UserDTO;
import edu.hfut.pojo.entity.Resource;
import edu.hfut.pojo.entity.User;
import edu.hfut.pojo.entity.UserRole;
import edu.hfut.service.IUserService;
import edu.hfut.util.comon.CommonResponse;
import edu.hfut.util.comon.JwtUtil;
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
public class UserServiceImpl implements IUserService {
    @Autowired
    private TkUserMapper tkUserMapper;

    @Autowired
    TkUserRoleMapper tkUserRoleMapper;
    @Autowired
    private SnowFlakeUtil snowFlakeUtil;

    @Override
    public CommonResponse getAllUser(UserDTO request) {
        return new CommonResponse(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg(),tkUserMapper.selectAll());
    }

    @Override
    public CommonResponse getCurrentPageUser(UserDTO request, Integer startPage, Integer pageSize) {
        PageHelper.startPage(startPage,pageSize);
        List<UserDTO> userDTOS = tkUserMapper.queryCurrentPageUserList(request.getName(), request.getUsername());
        PageInfo<UserDTO> pageInfo = new PageInfo<UserDTO>(userDTOS);
        return new CommonResponse(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg(),pageInfo);
    }

    @Override
    @Transactional
    public CommonResponse addUser(UserDTO request, String operatorId) {
        User userEntity = new User();
        BeanUtils.copyProperties(request,userEntity);
        try{
            Date date = new Date();
            userEntity.setCreatedBy(operatorId);
            userEntity.setCreatedTime(date);
            userEntity.setUpdatedBy(operatorId);
            userEntity.setUpdatedTime(date);
            userEntity.setVersion(1);
            long id = snowFlakeUtil.nextId();
            userEntity.setId(id+"");
            int count = tkUserMapper.insert(userEntity);
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
    public CommonResponse updateUser(UserDTO request, String operatorId) {
        User userEntity = new User();
        BeanUtils.copyProperties(request,userEntity);
        try{
            Example example = new Example(User.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("version", userEntity.getVersion());
            criteria.andEqualTo("id",userEntity.getId());
            userEntity.setUpdatedBy(operatorId);
            userEntity.setUpdatedTime(new Date());
            userEntity.setVersion(userEntity.getVersion()+1);
            int count = tkUserMapper.updateByExampleSelective(userEntity,example);
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
    public CommonResponse deleteUser(UserDTO request) {
        UserRole userRole = new UserRole();
        userRole.setUserId(request.getId());
        List<UserRole> roles = tkUserRoleMapper.select(userRole);
        if(roles != null && !roles.isEmpty()){
            throw new BussinessException(StatusCode.DEL_ERR_FOR_USER_ROLE_EXIT);
        }
        User userEntity = new User();
        BeanUtils.copyProperties(request,userEntity);
        try {
            tkUserMapper.deleteByPrimaryKey(userEntity.getId());
        }catch (Exception e){
            throw new BussinessException(StatusCode.DEL_ERR);
        }
        return new CommonResponse(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg());
    }

    @Override
    @Transactional
    public CommonResponse deleteMultipleUser(UserDTO request) {
        if(request.getUserIdList() == null || request.getUserIdList().isEmpty()){
            throw new BussinessException(StatusCode.DEL_ERR);
        }
        Integer count = tkUserRoleMapper.queryUserRoleCountByUserIds(request.getUserIdList());
        if(count != 0){
            throw new BussinessException(StatusCode.DEL_ERR_FOR_USER_ROLE_EXIT);
        }
        try {
            tkUserMapper.deleteMultipleUser(request.getUserIdList());
        }catch (Exception e){
            throw new BussinessException(StatusCode.DEL_ERR);
        }
        return new CommonResponse(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg());
    }

    @Override
    @Transactional
    public CommonResponse assignUsersForRoles(UserDTO request) {
        // 先判断
        if (request.getUserIdList() ==null || request.getRoleIdList() == null || request.getUserIdList().isEmpty() || request.getRoleIdList().isEmpty()){
            throw new BussinessException(StatusCode.ASSIGN_USER_ROLE_ERR);
        }
        Integer count;
        try{
            // 先删除所有用户的user_role关系
            Integer integer = tkUserMapper.clearUserRoleByUserId(request.getUserIdList());
            // 先构造一个新的userRole对象，给每一个用户添加新的user_role关系
            List<UserRole> userRoleByUserDTO = createUserRoleByUserDTO(request);
            count = tkUserRoleMapper.insertList(userRoleByUserDTO);
        } catch (Exception e){
            throw new BussinessException(StatusCode.ASSIGN_USER_ROLE_ERR);
        }

        return new CommonResponse(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg(), count);
    }

    @Override
    public CommonResponse login(UserDTO request) {
        User userEntity = new User();
        BeanUtils.copyProperties(request,userEntity);
        User user = tkUserMapper.selectOne(userEntity);
        if(user == null){
            return new CommonResponse(StatusCode.LOGIN_FAIL.getCode(),StatusCode.LOGIN_FAIL.getMsg());
        }
        Map result = new HashMap();
        System.out.println("userId:" + user.getId());
        result.put("token", JwtUtil.sign(user.getId()));
        result.put("user", user);
        return new CommonResponse(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg(),result);
    }

    @Override
    public CommonResponse getInfo(UserDTO request) {
        List<String> roleName = tkUserMapper.queryUserRole(request.getId());
        return new CommonResponse(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg(),roleName);
    }

    @Override
    public CommonResponse getUserResource(UserDTO request) {
        List<String> roleIds = this.getUserRoleIds(request.getId());
        List<Resource> userResources = null;
        if(!roleIds.isEmpty()){
            userResources = tkUserMapper.queryUserResourceByRoleIds(roleIds);
        }
        return new CommonResponse(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg(),userResources);
    }

    @Override
    public CommonResponse clearUserRoleByUserIdList(UserDTO request) {
        if(request.getUserIdList() == null  || request.getUserIdList().isEmpty()){
            throw new BussinessException(StatusCode.CLEAR_USER_ROLE_ERR);
        }
        try {
            // 根据UserIds清空所有关系
            tkUserMapper.clearRoleUserByUserId(request.getUserIdList());
        }catch (Exception e){
            throw new BussinessException(StatusCode.CLEAR_USER_ROLE_ERR);
        }
        return new CommonResponse(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg());
    }

    @Override
    public CommonResponse clearUserRoleByUserId(UserDTO request) {
        if(request.getId() == null || "".equals(request.getId())){
            throw new BussinessException(StatusCode.CLEAR_USER_ROLE_ERR);
        }
        UserRole userRole = new UserRole();
        userRole.setUserId(request.getId());
        try {
            // 根据UserIds清空所有关系
            tkUserRoleMapper.delete(userRole);
        }catch (Exception e){
            throw new BussinessException(StatusCode.CLEAR_USER_ROLE_ERR);
        }
        return  new CommonResponse(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg());
    }


    /**
     * 内部方法,根据用户id获取所有的角色信息列表
     * @param id
     * @return
     */
    private List<String> getUserRoleIds(String id){
        return tkUserMapper.queryUserRoleIds(id);
    }
    private List<UserRole> createUserRoleByUserDTO(UserDTO request) {
        List<String> userIdList = request.getUserIdList();
        List<String> roleIdList = request.getRoleIdList();
        List<UserRole> relative = new ArrayList<>(userIdList.size() * roleIdList.size());
        for(int i = 0; i < userIdList.size(); i++){
            for(int j = 0; j < roleIdList.size(); j++){
                UserRole userRole = new UserRole();
                userRole.setUserId(userIdList.get(i));
                userRole.setRoleId(roleIdList.get(j));
                relative.add(userRole);
            }
        }
        return relative;
    }
}
