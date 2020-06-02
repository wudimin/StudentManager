package com.damin.dao;

import com.damin.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDao {

    public User findUserByUserName(String username);

    //添加用户
    public int add(User user);

    //列表查询
    public List<User> findList(Map<String,Object> querMap);

    public int getTotal(Map<String,Object> querMap);

    //修改用户
    public int edit(User user);

    //删除用户
    public int delete(String ids);
}
