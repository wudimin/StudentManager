package com.damin.entity;

/*
 * 学生实体类
 */
public class Student {
    private long id;
    private String sn;
    private long clazzId;
    private String username;
    private String password;
    private String sex;
    private String photo;
    private String remark;

    public Student() {
    }

    public Student(long id, String sn, long clazzId, String username, String password, String sex, String photo, String remark) {
        this.id = id;
        this.sn = sn;
        this.clazzId = clazzId;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.photo = photo;
        this.remark = remark;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public long getClazzId() {
        return clazzId;
    }

    public void setClazzId(long clazzId) {
        this.clazzId = clazzId;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
