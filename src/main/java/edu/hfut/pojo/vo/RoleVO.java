package edu.hfut.pojo.vo;

import java.util.List;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-27 23:11
 */
public class RoleVO {
    /**
     * 角色基本信息
     */
    private String id;
    private String name;
    private String remark;
    /**
     * 有可能会被禁用掉
     */
    private Integer status;
    /**
     * 更新角色的时候version加一
     */
    private Integer version;

    /**
     * 这个角色有很多的用户，用户分配
     */
    private List<String> userIds;

    /**
     * 这个角色有很多的资源，有子资源，一定有父亲资源，资源分配
     */
    private List<String> resourceIds;
    /**
     * 批量删除角色时会用到这个字段，自己的批量删除
     */
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
