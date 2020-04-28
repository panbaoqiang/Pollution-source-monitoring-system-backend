package edu.hfut.pojo.entity;

import javax.persistence.*;
import java.util.Date;
/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-27 23:11
 */
@Table(name = "t_resource")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private String id;
    @Column
    private String name;
    @Column
    private String path;
    @Column
    private String btnPermissionValue;
    @Column
    private Integer isRedirect;
    @Column
    private String redirect;
    @Column
    private String parentId;
    @Column
    private String component;
    @Column
    private String icon;
    @Column
    private Integer resourceType;
    @Column
    private Integer leaf;
    @Column
    private String remark;
    @Column
    private Integer status;
    @Column
    private String createdBy;
    @Column
    private Date createdTime;
    @Column
    private String updatedBy;
    @Column
    private Date updatedTime;
    @Column
    private Integer version;

    @Override
    public String toString() {
        return "Resource{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", btnPermissionValue='" + btnPermissionValue + '\'' +
                ", isRedirect='" + isRedirect + '\'' +
                ", redirect='" + redirect + '\'' +
                ", parentId='" + parentId + '\'' +
                ", component='" + component + '\'' +
                ", icon='" + icon + '\'' +
                ", resourceType=" + resourceType +
                ", leaf=" + leaf +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", createdBy='" + createdBy + '\'' +
                ", createdTime=" + createdTime +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedTime=" + updatedTime +
                ", version=" + version +
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

    public Integer getIsRedirect() {
        return isRedirect;
    }

    public void setIsRedirect(Integer isRedirect) {
        this.isRedirect = isRedirect;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
