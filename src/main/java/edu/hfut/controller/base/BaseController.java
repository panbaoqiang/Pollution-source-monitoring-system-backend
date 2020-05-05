package edu.hfut.controller.base;

import edu.hfut.util.comon.CommonResponse;
import edu.hfut.util.comon.StatusCode;
import edu.hfut.util.exception.BussinessException;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author panbaoqiang
 * @Description 全局异常捕获
 * @create 2020-04-27 23:01
 */
@RestControllerAdvice
public abstract  class BaseController {
    private Logger logger = LoggerFactory.getLogger(BaseController.class);
    @org.springframework.web.bind.annotation.ExceptionHandler(value = BussinessException.class)
    public CommonResponse<Void> businessExpHandler(BussinessException e){
        logger.error("####################异常捕获,code:"+e.getCode()+",message:"+e.getMessage()+"######################");
        Integer expCode = e.getCode();
        String expMsg = e.getMessage();
        CommonResponse<Void> response = new CommonResponse<>(expCode,expMsg);
        return response;
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public CommonResponse<Void> expHandler(Exception e){
        logger.error("####################未定义异常######################");
        CommonResponse<Void> response = new CommonResponse<>(StatusCode.UNKNOWN_ERR.getCode(), StatusCode.UNKNOWN_ERR.getMsg());
        return response;
    }


    // 捕捉shiro的异常
    @ExceptionHandler(UnauthorizedException.class)
    public CommonResponse handle401(UnauthorizedException e) {
        CommonResponse<Void> response = new CommonResponse<>(StatusCode.UNAUTHORIZED_ERR.getCode(), StatusCode.UNAUTHORIZED_ERR.getMsg());
        return response;
    }
}