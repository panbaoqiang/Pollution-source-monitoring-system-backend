package edu.hfut.util.exception;

import edu.hfut.util.comon.StatusCode;

/**
 * @author panbaoqiang
 * @Description 系统异常
 * @create 2020-04-27 23:01
 */
public class BussinessException extends RuntimeException {
    private Integer code;
    public BussinessException(){}
    public BussinessException(StatusCode resultEnum){
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
}

