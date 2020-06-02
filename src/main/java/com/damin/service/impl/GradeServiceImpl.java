package com.damin.service.impl;

import com.damin.dao.GradeDao;
import com.damin.entity.Grade;
import com.damin.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GradeServiceImpl implements GradeService {
    @Autowired
    private GradeDao gradeDao;

    public int add(Grade grade) {
        return gradeDao.add(grade);
    }

    public List<Grade> findList(Map<String, Object> querMap) {
        return gradeDao.findList(querMap);
    }

    public int getTotal(Map<String, Object> querMap) {
        return gradeDao.getTotal(querMap);
    }

    public int edit(Grade grade) {
        return gradeDao.edit(grade);
    }

    public int delete(String ids) {
        return gradeDao.delete(ids);
    }

    public List<Grade> findAll() {
        return gradeDao.findAll();
    }
}
