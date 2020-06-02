package com.damin.dao;

import com.damin.entity.Clazz;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ClazzDao {
    //添加用户
    public int add(Clazz clazz);

    //列表查询
    public List<Clazz> findList(Map<String,Object> querMap);

    public int getTotal(Map<String,Object> querMap);

    //修改用户
    public int edit(Clazz clazz);

    //删除用户
    public int delete(String ids);

    public List<Clazz> findAll();
}
