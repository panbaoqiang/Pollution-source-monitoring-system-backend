package edu.hfut.util.comon;
/**
 * @author panbaoqiang
 * @Description 系统状态码
 * @create 2020-04-27 22:48
 */
public enum StatusCode {
    SUCCEED(20000,"操作成功"),
    LOGIN_FAIL(50055,"登入错误,请确保账号密码正确或者该账号未启用"),
    RESOURCE_GET_ERROR(30306,"获取资源失败"),
    ADD_ERR(30307,"添加失败"),
    DEL_ERR(30308,"删除失败"),
    DEL_ERR_FOR_RESOURCE_ROLE(30309,"删除失败,资源有对应的角色"),
    DEL_ERR_FOR_USER_ROLE_EXIT(30310,"删除失败,用户有关联的角色"),
    UPDATE_ERR(30309,"更新失败"),
    ASSIGN_USER_ROLE_ERR(30310,"分配用户角色失败"),
    ASSIGN_ROLE_RESOURCE_ERR(30311,"为角色分配资源失败"),
    CLEAR_USER_ROLE_ERR(30311,"清空用户角色信息失败"),
    ASSIGN_ROLE_USER_ERR(30312,"为角色分配用户失败"),
    UNKNOWN_ERR(99999,"未知错误");
    private Integer code;
    private String msg;
    StatusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public Integer getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}


