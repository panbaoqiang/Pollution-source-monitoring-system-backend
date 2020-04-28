package edu.hfut.pojo.vo;

import java.util.List;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-27 23:11
 */
public class ResourceVO {
    /**
     * 资源基本信息
     */
    private String id;
    private String name;
    private String path;
    private String btnPermissionValue;
    private String isRedirect;
    private String redirect;
    private String component;
    private String parentId;
    private String icon;
    private Integer resourceType;
    private Integer leaf;
    private String remark;
    /**
     * 资源是否禁用
     */
    private Integer status;
    /**
     * 更新的时候加一
     */
    private Integer version;

    @Override
    public String toString() {
        return "ResourceVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", btnPermissionValue='" + btnPermissionValue + '\'' +
                ", isRedirect='" + isRedirect + '\'' +
                ", redirect='" + redirect + '\'' +
                ", component='" + component + '\'' +
                ", parentId='" + parentId + '\'' +
                ", icon='" + icon + '\'' +
                ", resourceType=" + resourceType +
                ", leaf=" + leaf +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", version=" + version +
                ", resourceIdList=" + resourceIdList +
                '}';
    }

    /**
     * 批量删除资源
     */
    private List<String> resourceIdList;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBtnPermissionValue() {
        return btnPermissionValue;
    }

    public void setBtnPermissionValue(String btnPermissionValue) {
        this.btnPermissionValue = btnPermissionValue;
    }

    public String getIsRedirect() {
        return isRedirect;
    }

    public void setIsRedirect(String isRedirect) {
        this.isRedirect = isRedirect;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getLeaf() {
        return leaf;
    }

    public void setLeaf(Integer leaf) {
        this.leaf = leaf;
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

    public List<String> getResourceIdList() {
        return resourceIdList;
    }

    public void setResourceIdList(List<String> resourceIdList) {
        this.resourceIdList = resourceIdList;
    }
}
