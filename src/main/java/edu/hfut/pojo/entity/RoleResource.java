package edu.hfut.pojo.entity;

import javax.persistence.*;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-27 23:11
 */
@Table(name = "t_role_resource")
public class RoleResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Long id;
    @Column
    private String roleId;
    @Column
    private String resourceId;

    @Override
    public String toString() {
        return "RoleResource{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", resourceId=" + resourceId +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
