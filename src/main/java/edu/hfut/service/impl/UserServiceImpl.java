package edu.hfut.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.hfut.dao.TkUserMapper;
import edu.hfut.pojo.dto.UserDTO;
import edu.hfut.pojo.entity.Resource;
import edu.hfut.pojo.entity.User;
import edu.hfut.service.IUserService;
import edu.hfut.util.comon.CommonResponse;
import edu.hfut.util.comon.JwtUtil;
import edu.hfut.util.comon.SnowFlakeUtil;
import edu.hfut.util.comon.StatusCode;
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
    private SnowFlakeUtil snowFlakeUtil;

    @Override
    public CommonResponse getAllUser(UserDTO request) {
        return new CommonResponse(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg(),tkUserMapper.selectAll());
    }

    @Override
    public CommonResponse getCurrentPageUser(UserDTO request, Integer startPage, Integer pageSize) {
        return null;
    }

    @Override
    public CommonResponse addUser(UserDTO request, String operatorId) {
        return null;
    }

    @Override
    public CommonResponse updateUser(UserDTO request, String operatorId) {
        return null;
    }

    @Override
    public CommonResponse deleteUser(UserDTO request) {
        return null;
    }

    @Override
    public CommonResponse deleteMultipleUser(UserDTO request) {
        return null;
    }

    @Override
    public CommonResponse assignUsersForRoles(UserDTO request) {
        return null;
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

    /**
     * 内部方法,根据用户id获取所有的角色信息列表
     * @param id
     * @return
     */
    private List<String> getUserRoleIds(String id){
        return tkUserMapper.queryUserRoleIds(id);
    }
}
