package com.damin.service.impl;

import com.damin.dao.ClazzDao;
import com.damin.entity.Clazz;
import com.damin.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzDao clazzDao;
    public int add(Clazz clazz) {
        return clazzDao.add(clazz);
    }

    public List<Clazz> findList(Map<String, Object> querMap) {
        return clazzDao.findList(querMap);
    }

    public int getTotal(Map<String, Object> querMap) {
        return clazzDao.getTotal(querMap);
    }

    public int edit(Clazz clazz) {
        return clazzDao.edit(clazz);
    }

    public int delete(String ids) {
        return clazzDao.delete(ids);
    }

    public List<Clazz> findAll() {
        return clazzDao.findAll();
    }
}
