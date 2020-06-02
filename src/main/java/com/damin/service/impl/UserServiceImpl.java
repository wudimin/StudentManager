package com.damin.service.impl;

import com.damin.dao.UserDao;
import com.damin.entity.User;
import com.damin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    public User findUserByUserName(String username) {

        return userDao.findUserByUserName(username);
    }

    public int add(User user) {
        return userDao.add(user);
    }

    public List<User> findList(Map<String, Object> querMap) {
        return userDao.findList(querMap);
    }

    public int getTotal(Map<String, Object> querMap) {
        return userDao.getTotal(querMap);
    }

    public int edit(User user) {
        return userDao.edit(user);
    }

    public int delete(String ids) {
        return userDao.delete(ids);
    }
}
