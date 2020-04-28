package edu.hfut.api;

import edu.hfut.pojo.vo.RoleVO;
import edu.hfut.util.comon.CommonRequest;
import edu.hfut.util.comon.CommonResponse;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-27 23:09
 */

public interface RoleApi {
    /**
     * 获取所有角色，不管是否禁用
     * @param request
     * @return
     */
    CommonResponse getAllRole(CommonRequest<RoleVO> request) ;

    /**
     * 获取当前页面角色，不管是否禁用
     * @param request
     * @return
     */
    CommonResponse getCurrentPageRole( CommonRequest<RoleVO> request) ;

    /**
     * 增加一个角色
     * @param request
     * @return
     */
    CommonResponse addRole( CommonRequest<RoleVO> request) ;

    /**
     * 更新一个角色的基本信息，version加一
     * @param request
     * @return
     */
    CommonResponse updateRole( CommonRequest<RoleVO> request) ;

    /**
     * 删除一个角色，那么这个角色数据库里面的两个关联表都要清除
     * @param request
     * @return
     */
    @PostMapping("deleteRole")
    CommonResponse deleteRole( CommonRequest<RoleVO> request);

    /**
     * 删除多个角色,那么这个角色数据库里面的两个关联表都要清除
     * @param request
     * @return
     */
    @PostMapping("deleteMultipleRole")
    CommonResponse deleteMultipleRole( CommonRequest<RoleVO> request);

    /**
     * 为若干个角色分配若干个资源，先删除这些角色的所有资源，然后在在重新赋值
     * @param request
     * @return
     */
    CommonResponse assignRolesForResources( CommonRequest<RoleVO> request) ;

    /**
     * 将若干个角色分配给用户，将用户的所有角色先删除，然后在一个赋予这些批量的角色
     * @param request
     * @return
     */
    CommonResponse assignRolesForUsers( CommonRequest<RoleVO> request) ;

}
