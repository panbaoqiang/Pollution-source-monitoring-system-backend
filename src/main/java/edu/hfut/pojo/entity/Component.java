package edu.hfut.pojo.entity;

import javax.persistence.*;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-28 9:30
 */
@Table(name = "t_component")
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Long id;
    @Column
    private String name;

    @Override
    public String toString() {
        return "Component{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", component='" + component + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    @Column
    private String component;

}
