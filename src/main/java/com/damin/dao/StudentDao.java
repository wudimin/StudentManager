package com.damin.dao;

import com.damin.entity.Student;
import com.damin.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StudentDao {

    public Student findUserByUserName(String username);
    public int add(Student student);
    public List<Student> findList(Map<String, Object> querMap);
    public int getTotal(Map<String, Object> querMap);
    public int edit(Student student);
    public int delete(String ids);
    public List<Student> findAll();
}
