package edu.hfut.pojo.dto;

import java.util.List;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-27 23:16
 */
public class RoleDTO {
    private String id;
    private String name;
    private String remark;
    private Integer status;
    private Integer version;
    private List<String> userIds;
    private List<String> resourceIds;
    private List<String> roleIdList;

    @Override
    public String toString() {
        return "RoleVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", version=" + version +
                ", userIds=" + userIds +
                ", resourceIds=" + resourceIds +
                ", roleIdList=" + roleIdList +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public List<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public List<String> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<String> roleIdList) {
        this.roleIdList = roleIdList;
    }
}
