package com.damin.service;

import com.damin.entity.Grade;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Component
public interface GradeService {
    public int add(Grade grade);
    public List<Grade> findList(Map<String,Object> querMap);
    public int getTotal(Map<String,Object> querMap);
    public int edit(Grade grade);
    public int delete(String ids);
    public List<Grade> findAll();
}
