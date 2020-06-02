package com.damin.service;

import com.damin.entity.Clazz;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ClazzService {
    public int add(Clazz Clazz);
    public List<Clazz> findList(Map<String,Object> querMap);
    public int getTotal(Map<String,Object> querMap);
    public int edit(Clazz Clazz);
    public int delete(String ids);
    public List<Clazz> findAll();
}
