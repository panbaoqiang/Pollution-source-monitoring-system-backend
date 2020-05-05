package edu.hfut.service;

import edu.hfut.pojo.dto.RoleDTO;
import edu.hfut.pojo.dto.UserDTO;
import edu.hfut.pojo.entity.User;
import edu.hfut.util.comon.CommonResponse;

import java.util.List;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-27 23:11
 */
public interface IUserService  {
    /**
     * 获取所有用户
     * @param request
     * @return
     */
    CommonResponse getAllUser(UserDTO request) ;

    /**
     * 获取当前页的用户信息列表
     * @param request
     * @return
     */
    CommonResponse getCurrentPageUser(UserDTO request, Integer startPage, Integer pageSize) ;

    /**
     * 增加一个用户
     * @param request
     * @return
     */
    CommonResponse addUser(UserDTO request, String operatorId) ;

    /**
     * 更新一个用户，当然是基本信息的更新
     * @param request
     * @return
     */
    CommonResponse updateUser(UserDTO request, String operatorId) ;

    /**
     * 删除一个用户
     * @param request
     * @return
     */
    CommonResponse deleteUser(UserDTO request);

    /**
     * 删除多个用户
     * @param request
     * @return
     */
    CommonResponse deleteMultipleUser(UserDTO request);

    /**
     * 在用户已经拥有角色的基础上添加新的角色，如果角色已经存在，就覆盖之前的角色
     * @param request
     * @return
     */
    CommonResponse assignUsersForRoles(UserDTO request);

    /**
     * 用户登入，获取用户名密码，然后可能用户名密码为空，这时候返回异常
     * @param request
     * @return
     */
    CommonResponse login(UserDTO request);

    /**
     * 获取登入用户的角色列表
     * @param request
     * @return
     */
    CommonResponse getInfo(UserDTO request);


    /**
     * 获取登入用户的所有资源信息
     * @param request
     * @return
     */
    CommonResponse getUserResource(UserDTO request);

    /**
     * 根据UserIds清空用户的角色信息表
     * @param request
     * @return
     */
    CommonResponse clearUserRoleByUserIdList(UserDTO request) ;

    /**
     * 根据U单个用户id清空用户的角色信息表
     * @param request
     * @return
     */
    CommonResponse clearUserRoleByUserId(UserDTO request) ;

    /**
     * 根据 用户的用户名查询用户实体
     * @param username
     * @return
     */
    User queryUserByUserName(String username);

    /**
     * 获取登入用户的角色列表
     * @param user
     * @return
     */
    List<String> getRoleList(User user);
}
