package edu.hfut.api;

import edu.hfut.pojo.vo.UserVO;
import edu.hfut.util.comon.CommonRequest;
import edu.hfut.util.comon.CommonResponse;
import org.springframework.web.bind.annotation.*;
/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-27 23:09
 */

public interface UserApi {
    /**
     * 获取所有用户
     * @param request
     * @return
     */
    CommonResponse getAllUser(@RequestBody CommonRequest<UserVO> request) ;

    /**
     * 获取当前页的用户信息列表
     * @param request
     * @return
     */
    CommonResponse getCurrentPageUser(@RequestBody CommonRequest<UserVO> request) ;

    /**
     * 增加一个用户
     * @param request
     * @return
     */
    CommonResponse addUser(@RequestBody CommonRequest<UserVO> request) ;

    /**
     * 更新一个用户，当然是基本信息的更新
     * @param request
     * @return
     */
    CommonResponse updateUser(@RequestBody CommonRequest<UserVO> request) ;

    /**
     * 删除一个用户
     * @param request
     * @return
     */
    CommonResponse deleteUser(@RequestBody CommonRequest<UserVO> request);

    /**
     * 删除多个用户
     * @param request
     * @return
     */
    CommonResponse deleteMultipleUser(@RequestBody CommonRequest<UserVO> request);

    /**
     * 在用户已经拥有角色的基础上添加新的角色，如果角色已经存在，就覆盖之前的角色
     * @param request
     * @return
     */
    CommonResponse assignUsersForRoles(@RequestBody CommonRequest<UserVO> request);

    /**
     * 用户登入，获取用户名密码，然后可能用户名密码为空，这时候返回异常
     * @param request
     * @return
     */
    CommonResponse login(@RequestBody CommonRequest<UserVO> request);

    /**
     * 获取登入用户的角色列表
     * @param request
     * @return
     */
    CommonResponse getInfo(@RequestBody CommonRequest<UserVO> request);


    /**
     * 获取登入用户的所有资源信息
     * @param request
     * @return
     */
    CommonResponse getUserResource(@RequestBody CommonRequest<UserVO> request);

}
