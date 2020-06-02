package com.damin.dao;

import com.damin.entity.Grade;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GradeDao {
    //添加用户
    public int add(Grade grade);

    //列表查询
    public List<Grade> findList(Map<String,Object> querMap);

    public int getTotal(Map<String,Object> querMap);

    //修改用户
    public int edit(Grade grade);

    //删除用户
    public int delete(String ids);

    public List<Grade> findAll();
}
