package com.damin.service;

import com.damin.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Component
public interface UserService {

    public User findUserByUserName(String username);
    public int add(User user);
    public List<User> findList(Map<String,Object> querMap);
    public int getTotal(Map<String,Object> querMap);
    public int edit(User user);
    public int delete(String ids);
}
