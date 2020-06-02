package com.damin.entity;

import org.springframework.stereotype.Component;

/*
 * 年级实体类
 */
@Component
public class Grade {
    private long id;
    private String name;
    private String remark; //备注

    public Grade() {
    }

    public Grade(long id, String name, String remark) {
        this.id = id;
        this.name = name;
        this.remark = remark;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
