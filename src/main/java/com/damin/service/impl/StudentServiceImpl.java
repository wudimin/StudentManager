package com.damin.service.impl;

import com.damin.dao.StudentDao;
import com.damin.entity.Student;
import com.damin.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/*
 * 学生Service实现
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    public Student findUserByUserName(String username) {
        return studentDao.findUserByUserName(username);
    }

    public int add(Student student) {
        return studentDao.add(student);
    }

    public List<Student> findList(Map<String, Object> querMap) {
        return studentDao.findList(querMap);
    }

    public int getTotal(Map<String, Object> querMap) {
        return studentDao.getTotal(querMap);
    }

    public int edit(Student student) {
        return studentDao.edit(student);
    }

    public int delete(String ids) {
        return studentDao.delete(ids);
    }

    public List<Student> findAll() {
        return studentDao.findAll();
    }
}
