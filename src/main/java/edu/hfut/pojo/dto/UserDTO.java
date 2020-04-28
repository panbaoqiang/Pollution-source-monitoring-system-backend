package edu.hfut.pojo.dto;

import edu.hfut.pojo.entity.Role;

import java.util.List;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-27 23:16
 */
public class UserDTO {
    private String id;
    private String username;
    private String password;
    private String name;
    private String tel;
    private String remark;
    private Integer status;
    private Integer version;
    private List<String> userIdList;
    /**
     * 冗余一个字段，这个歌跟下面是一样的
     */
    private List<String> roleIdList;

    private List<Role> roleList;

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

    @Override
    public String toString() {
        return "UserDTO{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", version=" + version +
                ", userIdList=" + userIdList +
                ", roleIdList=" + roleIdList +
                ", roleList=" + roleList +
                '}';
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    public List<String> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<String> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
