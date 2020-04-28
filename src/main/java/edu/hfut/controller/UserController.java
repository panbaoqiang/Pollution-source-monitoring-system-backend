package edu.hfut.controller;

import edu.hfut.api.UserApi;
import edu.hfut.controller.base.BaseController;
import edu.hfut.pojo.dto.UserDTO;
import edu.hfut.pojo.vo.UserVO;
import edu.hfut.service.IUserService;
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
 * @Description 用户控制层
 * @create 2020-04-27 23:08
 */
@RestController
@RequestMapping("user")
public class UserController  extends BaseController implements UserApi {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @Override
    @PostMapping("getAllUser")
    public CommonResponse getAllUser(@RequestBody CommonRequest<UserVO> request) {
        return userService.getAllUser(null);
    }

    @Override
    @PostMapping("getCurrentPageUser")
    public CommonResponse getCurrentPageUser(@RequestBody  CommonRequest<UserVO> request) {
        return null;
    }

    @Override
    @PostMapping("addUser")
    public CommonResponse addUser(@RequestBody  CommonRequest<UserVO> request) {
        return null;
    }

    @Override
    @PostMapping("updateUser")
    public CommonResponse updateUser(@RequestBody  CommonRequest<UserVO> request) {
        return null;
    }

    @Override
    @PostMapping("deleteUser")
    public CommonResponse deleteUser(@RequestBody  CommonRequest<UserVO> request) {
        return null;
    }

    @Override
    @PostMapping("deleteMultipleUser")
    public CommonResponse deleteMultipleUser(@RequestBody CommonRequest<UserVO> request) {
        return null;
    }

    @Override
    @PostMapping("assignUsersForRoles")
    public CommonResponse assignUsersForRoles(@RequestBody CommonRequest<UserVO> request) {
        return null;
    }

    @Override
    @PostMapping("login")
    public CommonResponse login(@RequestBody CommonRequest<UserVO> request) {
        logger.info(request.toString());
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(request.getData(),userDTO);
        return userService.login(userDTO);
    }

    @Override
    @PostMapping("getInfo")
    public CommonResponse getInfo(@RequestBody CommonRequest<UserVO> request) {
        logger.info(request.toString());
        UserDTO userDTO = new UserDTO();
        userDTO.setId(request.getOperatorId());
        return userService.getInfo(userDTO);
    }

    @Override
    @PostMapping("getUserResource")
    public CommonResponse getUserResource(@RequestBody CommonRequest<UserVO> request) {
        logger.info(request.toString());
        UserDTO userDTO = new UserDTO();
        userDTO.setId(request.getOperatorId());
        return userService.getUserResource(userDTO);
    }
}
