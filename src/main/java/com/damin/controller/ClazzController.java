package com.damin.controller;

import com.damin.entity.Clazz;
import com.damin.page.Page;
import com.damin.service.ClazzService;
import com.damin.service.GradeService;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/clazz")
@Controller
public class ClazzController {

    @Autowired
    private ClazzService clazzService;
    @Autowired
    private GradeService gradeService;

    //班级管理界面
    @GetMapping("/list")
    public ModelAndView list(ModelAndView model){
        model.setViewName("clazz/clazz_list");
        model.addObject("gradeList",gradeService.findAll());
        model.addObject("gradeListJson", JSONArray.fromObject(gradeService.findAll()));

        return model;
    }

    //显示班级信息列表
    @PostMapping("/get_list")
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "name",defaultValue = "",required = false) String name,
            @RequestParam(value = "gradeId",required = false) Long gradeId,
            Page page){
        Map<String,Object> ret = new HashMap<String, Object>();
        Map<String,Object> querMap = new HashMap<String, Object>();

        if (gradeId != null){
            querMap.put("gradeId",gradeId);
        }
        querMap.put("name","%"+name+"%");
        //分页
        querMap.put("offset",page.getOffset());
        querMap.put("pageSize",page.getRows());
        ret.put("rows",clazzService.findList(querMap));
        ret.put("total",clazzService.getTotal(querMap));
        return ret;
    }

    //班级信息的添加
    @PostMapping("/add")
    @ResponseBody
    public Map<String,String> add(Clazz clazz){
        Map<String,String> ret = new HashMap<String, String>();

        if (StringUtils.isEmpty(clazz.getName())){
            ret.put("type","error");
            ret.put("msg","班级名称不能为空！");
            return ret;
        }
        if (clazz.getGradeId() == 0L){
            ret.put("type","error");
            ret.put("msg","请选择所属年级！");
            return ret;
        }

        if (clazzService.add(clazz) <= 0){
            ret.put("type","error");
            ret.put("msg","添加失败");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","添加成功");
        return ret;
    }

    //班级修改
    @PostMapping("/edit")
    @ResponseBody
    public Map<String,String> edit(Clazz clazz){

        Map<String,String> ret = new HashMap<String, String>();
        if (clazz == null){
            ret.put("type","error");
            ret.put("msg","请联系系统开发人员！");
            return ret;
        }
        if (clazz.getGradeId() == 0L){
            ret.put("type","error");
            ret.put("msg","所属年级不能为空！");
            return ret;
        }
        if (StringUtils.isEmpty(clazz.getName())){
            ret.put("type","error");
            ret.put("msg","班级名称不能为空！");
            return ret;
        }

        if (clazzService.edit(clazz) <= 0){
            ret.put("type","error");
            ret.put("msg","修改失败！");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","修改成功");
        return ret;
    }

    //删除班级信息
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
            if (clazzService.delete(str) <= 0){
                ret.put("type","error");
                ret.put("msg","删除失败！");
                return ret;
            }
        }catch (Exception e){
            ret.put("type","error");
            ret.put("msg","该班级下存在学生信息，不能删除！");
            return ret;
        }

        ret.put("type","success");
        ret.put("msg","删除成功");
        return ret;
    }
}