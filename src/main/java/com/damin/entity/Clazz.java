package com.damin.entity;

import org.springframework.stereotype.Component;

@Component
public class Clazz {
    private long id;
    private long gradeId;
    private String name;
    private String remark;

    public Clazz() {
    }

    public Clazz(long id, long gradeId, String name, String remark) {
        this.id = id;
        this.gradeId = gradeId;
        this.name = name;
        this.remark = remark;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGradeId() {
        return gradeId;
    }

    public void setGradeId(long gradeId) {
        this.gradeId = gradeId;
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
}
