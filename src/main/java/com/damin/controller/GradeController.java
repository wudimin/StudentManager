package com.damin.controller;

import com.damin.entity.Grade;
import com.damin.page.Page;
import com.damin.service.GradeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/*
 * 年级
 */
@RequestMapping("/grade")
@Controller
public class GradeController {

    @Autowired
    private GradeService gradeService;

    //年级管理界面
    @GetMapping("/list")
    public ModelAndView list(ModelAndView model){
        model.setViewName("grade/grade_list");
        return model;
    }

    //显示年级列表
    @PostMapping("/get_list")
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "name",defaultValue = "",required = false) String name,
            Page page){
        Map<String,Object> ret = new HashMap<String, Object>();
        Map<String,Object> querMap = new HashMap<String, Object>();
        querMap.put("name","%"+name+"%");
        //分页
        querMap.put("offset",page.getOffset());
        querMap.put("pageSize",page.getRows());
        ret.put("rows",gradeService.findList(querMap));
        ret.put("total",gradeService.getTotal(querMap));
        return ret;
    }

    //年级信息的添加
    @PostMapping("/add")
    @ResponseBody
    public Map<String,String> add(Grade grade){
        Map<String,String> ret = new HashMap<String, String>();

        if (StringUtils.isEmpty(grade.getName())){
            ret.put("type","error");
            ret.put("msg","年级名称不能为空！");
            return ret;
        }

        if (gradeService.add(grade) <= 0){
            ret.put("type","error");
            ret.put("msg","添加失败");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","添加成功");
        return ret;
    }

    //年级修改
    @PostMapping("/edit")
    @ResponseBody
    public Map<String,String> edit(Grade grade){

        Map<String,String> ret = new HashMap<String, String>();
        if (grade == null){
            ret.put("type","error");
            ret.put("msg","请联系系统开发人员！");
            return ret;
        }
        if (StringUtils.isEmpty(grade.getName())){
            ret.put("type","error");
            ret.put("msg","年级名称不能为空！");
            return ret;
        }

        if (gradeService.edit(grade) <= 0){
            ret.put("type","error");
            ret.put("msg","修改失败！");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","修改成功");
        return ret;
    }

    //删除年级信息
    @PostMapping("/delete")
    @ResponseBody
    public Map<String,String> delete(@RequestParam(value = "ids[]",required = true) long[] ids) {

        Map<String, String> ret = new HashMap<String, String>();
        if (ids == null){
            ret.put("type","error");
            ret.put("msg","您还没有选择任何数据哦！");
            return ret;
        }
        try {
            String str = "";
            for (long id : ids) {
                str += id + ",";
            }
            str = str.substring(0,str.length()-1);
            if (gradeService.delete(str) <= 0){
                ret.put("type","error");
                ret.put("msg","删除失败！");
                return ret;
            }
        }catch (Exception e){
            ret.put("type","error");
            ret.put("msg","该年级下存在班级，不能删除！");
            return ret;
        }

        ret.put("type","success");
        ret.put("msg","删除成功");
        return ret;
    }
}
