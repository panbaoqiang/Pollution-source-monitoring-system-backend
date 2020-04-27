package edu.hfut.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.hfut.pojo.dto.RoleDTO;
import edu.hfut.service.IRoleService;
import edu.hfut.util.comon.CommonResponse;
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

    @Override
    public CommonResponse getAllRole(RoleDTO request) {
        return null;
    }

    @Override
    public CommonResponse getCurrentPageRole(RoleDTO request, Integer startPage, Integer pageSize) {
        return null;
    }

    @Override
    public CommonResponse addRole(RoleDTO request, String operatorId) {
        return null;
    }

    @Override
    public CommonResponse updateRole(RoleDTO request, String operatorId) {
        return null;
    }

    @Override
    public CommonResponse deleteRole(RoleDTO request) {
        return null;
    }

    @Override
    public CommonResponse deleteMultipleRole(RoleDTO request) {
        return null;
    }

    @Override
    public CommonResponse assignRolesForResources(RoleDTO request) {
        return null;
    }

    @Override
    public CommonResponse assignRolesForUsers(RoleDTO request) {
        return null;
    }
}
