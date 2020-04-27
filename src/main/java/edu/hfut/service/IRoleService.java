package edu.hfut.service;

import edu.hfut.pojo.dto.RoleDTO;
import edu.hfut.util.comon.CommonResponse;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-27 23:11
 */
public interface IRoleService {
    /**
     * 获取所有角色，默认都是status为1
     * @param request
     * @return
     */
    CommonResponse getAllRole(RoleDTO request) ;

    /**
     * 获取当前页面角色，status不要求
     * @param request
     * @return
     */
    CommonResponse getCurrentPageRole(RoleDTO request, Integer startPage, Integer pageSize) ;

    /**
     * 增加一个角色
     * @param request
     * @return
     */
    CommonResponse addRole(RoleDTO request, String operatorId) ;

    /**
     * 更新一个角色的基本信息，version加一
     * @param request
     * @return
     */
    CommonResponse updateRole(RoleDTO request, String operatorId) ;

    /**
     * 删除一个角色，那么这个角色数据库里面的两个关联表都要清除
     * @param request
     * @return
     */
    CommonResponse deleteRole(RoleDTO request);

    /**
     * 删除多个角色,那么这个角色数据库里面的两个关联表都要清除
     * @param request
     * @return
     */
    CommonResponse deleteMultipleRole(RoleDTO request);

    /**
     * 为若干个角色分配若干个资源，先删除这些角色的所有资源，然后在在重新赋值
     * @param request
     * @return
     */
    CommonResponse assignRolesForResources(RoleDTO request) ;

    /**
     * 将若干个角色分配给用户，将用户的所有角色先删除，然后在一个赋予这些批量的角色
     * @param request
     * @return
     */
    CommonResponse assignRolesForUsers(RoleDTO request) ;
}
