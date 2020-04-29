package edu.hfut.controller;

import edu.hfut.api.RoleApi;
import edu.hfut.controller.base.BaseController;
import edu.hfut.pojo.dto.RoleDTO;
import edu.hfut.pojo.vo.RoleVO;
import edu.hfut.service.IRoleService;
import edu.hfut.util.comon.CommonRequest;
import edu.hfut.util.comon.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-27 23:09
 */
@RestController
@RequestMapping("role")
public class RoleController extends BaseController implements RoleApi {
    private Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private IRoleService roleService;

    @Override
    @PostMapping("getAllRole")
    public CommonResponse getAllRole(@RequestBody CommonRequest<RoleVO> request) {
        logger.info(request.toString());
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(request.getData(),roleDTO);
        return roleService.getAllRole(roleDTO);
    }

    @Override
    @PostMapping("getCurrentPageRole")
    public CommonResponse getCurrentPageRole(@RequestBody CommonRequest<RoleVO> request) {
        logger.info(request.toString());
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(request.getData(),roleDTO);
        return roleService.getCurrentPageRole(roleDTO,request.getStartPage(),request.getPageSize());
    }

    @Override
    @PostMapping("addRole")
    public CommonResponse addRole(@RequestBody CommonRequest<RoleVO> request) {
        logger.info(request.toString());
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(request.getData(),roleDTO);
        return roleService.addRole(roleDTO, request.getOperatorId());
    }

    @Override
    @PostMapping("updateRole")
    public CommonResponse updateRole(@RequestBody CommonRequest<RoleVO> request) {
        logger.info(request.toString());
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(request.getData(),roleDTO);
        return roleService.updateRole(roleDTO, request.getOperatorId());
    }

    @Override
    @PostMapping("deleteRole")
    public CommonResponse deleteRole(@RequestBody CommonRequest<RoleVO> request) {
        logger.info(request.toString());
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(request.getData(),roleDTO);
        return roleService.deleteRole(roleDTO);
    }

    @Override
    @PostMapping("deleteMultipleRole")
    public CommonResponse deleteMultipleRole(@RequestBody CommonRequest<RoleVO> request) {
        logger.info(request.toString());
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(request.getData(),roleDTO);
        return roleService.deleteMultipleRole(roleDTO);
    }

    @Override
    @PostMapping("assignRoleForResource")
    public CommonResponse assignRolesForResources(@RequestBody CommonRequest<RoleVO> request) {
        logger.info(request.toString());
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(request.getData(),roleDTO);
        return roleService.assignRolesForResources(roleDTO);
    }

    @Override
    @PostMapping("assignRoleForUser")
    public CommonResponse assignRolesForUsers(@RequestBody CommonRequest<RoleVO> request) {
        logger.info(request.toString());
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(request.getData(),roleDTO);
        return roleService.assignRolesForUsers(roleDTO);
    }
}
