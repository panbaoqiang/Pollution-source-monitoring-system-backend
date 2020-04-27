package edu.hfut.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.hfut.dao.TkUserMapper;
import edu.hfut.pojo.dto.UserDTO;
import edu.hfut.service.IUserService;
import edu.hfut.util.comon.CommonResponse;
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
    private TkUserMapper userMapper;

    @Override
    public CommonResponse getAllUser(UserDTO request) {
        return new CommonResponse(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg(),userMapper.selectAll());
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
        return null;
    }

    @Override
    public CommonResponse getInfo(UserDTO request) {
        return null;
    }

    @Override
    public CommonResponse getUserResource(UserDTO request) {
        return null;
    }
}
