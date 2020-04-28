package edu.hfut.pojo.entity;

import javax.persistence.*;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-28 9:29
 */
@Table(name = "t_icon")
public class Icon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Long id;
    @Column
    private String name;
    @Column
    private String icon;

    @Override
    public String toString() {
        return "Icon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
