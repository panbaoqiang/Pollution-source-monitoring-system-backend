package edu.hfut.pojo.vo;

import java.util.List;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-27 23:11
 */
public class UserVO {
    /**
     * 用户最基本信息，id的时候会用到，删除单个用户有点不切实际
     **/
    private String id;
    private String username;
    private String password;
    private String name;
    private String tel;
    private String remark;
    /**
     * 可能会批量删除用户
     */
    private List<String> userIdList;
    /**
     * 可能要禁用用户
     */
    private Integer status;
    /**
     * 可能要修改用户，所以返回他的版本号
     */
    private Integer version;
    /**
     * 要删除他原本的角色，然后统一分配，角色分配
     */
    private List<String> roleIdList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<String> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<String> roleIdList) {
        this.roleIdList = roleIdList;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", remark='" + remark + '\'' +
                ", userIdList=" + userIdList +
                ", status=" + status +
                ", version=" + version +
                ", roleIdList=" + roleIdList +
                '}';
    }
}
