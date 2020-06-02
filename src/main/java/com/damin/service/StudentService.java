package com.damin.service;

import com.damin.entity.Student;
import com.damin.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface StudentService {

    public Student findUserByUserName(String username);
    public int add(Student student);
    public List<Student> findList(Map<String, Object> querMap);
    public int getTotal(Map<String, Object> querMap);
    public int edit(Student student);
    public int delete(String ids);
    public List<Student> findAll();
}
