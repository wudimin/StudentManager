package com.damin.controller;

import com.damin.entity.Clazz;
import com.damin.entity.Student;
import com.damin.page.Page;
import com.damin.service.ClazzService;
import com.damin.service.GradeService;
import com.damin.service.StudentService;
import com.damin.util.StringUtil;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RequestMapping("/student")
@Controller
public class StudentController {

    @Autowired
    private ClazzService clazzService;
    @Autowired
    private StudentService studentService;

    //学生管理界面
    @GetMapping("/list")
    public ModelAndView list(ModelAndView model){
        model.setViewName("student/student_list");
        //model.addObject("gradeList",gradeService.findAll());
        //model.addObject("gradeListJson", JSONArray.fromObject(gradeService.findAll()));
        model.addObject("clazzList",clazzService.findAll());
        model.addObject("clazzListJson", JSONArray.fromObject(clazzService.findAll()));
        return model;
    }

    //显示学生信息列表
    @PostMapping("/get_list")
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "name",defaultValue = "",required = false) String name,
            @RequestParam(value = "clazzId",required = false) Long clazzId,
            Page page,
            HttpServletRequest request){
        Map<String,Object> ret = new HashMap<String, Object>();
        Map<String,Object> querMap = new HashMap<String, Object>();

        if (clazzId != null){
            querMap.put("clazzId",clazzId );
        }
        querMap.put("username","%"+name+"%");

        Object attribute = request.getSession().getAttribute("userType");
        if ("2".equals(attribute.toString())) {
            Student loginStudent = (Student) request.getSession().getAttribute("user");
            querMap.put("username","%"+loginStudent.getUsername()+"%");
        }

        //分页
        querMap.put("offset",page.getOffset());
        querMap.put("pageSize",page.getRows());
        ret.put("rows",studentService.findList(querMap));
        ret.put("total",studentService.getTotal(querMap));
        return ret;
    }

    //上传学生头像
    @PostMapping("/upload_photo")
    @ResponseBody
    public Map<String,String> uploadPhoto(MultipartFile photo,
                                          HttpServletRequest request,
                                          HttpServletResponse response) throws IOException {
        Map<String,String> ret = new HashMap<String, String>();

        if (photo == null){
            ret.put("type","error");
            ret.put("msg","请选择文件！");
            return ret;
        }
        if (photo.getSize() > 10485760){
            ret.put("type","error");
            ret.put("msg","文件过大，请上传小于10M的文件！");
            return ret;
        }

        String suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".")+1, photo.getOriginalFilename().length());
        if ( !"jpg,png,gif,jpeg".contains(suffix.toLowerCase())){
            ret.put("type","error");
            ret.put("msg","文件格式错误，请重新上传！");
            return ret;
        }

        String savePath = request.getServletContext().getRealPath("/")+"\\upload\\";
        //System.out.println("路径路径——————————————————————"+savePath);
        File savePathFile = new File(savePath);
        if (!savePathFile.exists()){
            savePathFile.mkdir();
        }
        String filename = new Date().getTime()+"."+suffix;
        photo.transferTo(new File(savePath+filename));
        ret.put("type","success");
        ret.put("msg","上传成功");
        ret.put("src",request.getServletContext().getContextPath()+"/upload/"+filename);
        return ret;
    }

    //学生信息的添加
    @PostMapping("/add")
    @ResponseBody
    public Map<String,String> add(Student student){
        Map<String,String> ret = new HashMap<String, String>();

        if (StringUtils.isEmpty(student.getUsername())){
            ret.put("type","error");
            ret.put("msg","学生姓名不能为空！");
            return ret;
        }
        if (StringUtils.isEmpty(student.getPassword())){
            ret.put("type","error");
            ret.put("msg","登录密码不能为空！");
            return ret;
        }

        if (isExit(student.getUsername(),0L)) {
            ret.put("type","error");
            ret.put("msg","用户已存在！");
            return ret;
        }

        if (student.getClazzId() == 0L){
            ret.put("type","error");
            ret.put("msg","请选择所属班级！");
            return ret;
        }

        student.setSn(StringUtil.generateSn("S",""));

        if (studentService.add(student) <= 0){
            ret.put("type","error");
            ret.put("msg","添加失败");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","添加成功");
        return ret;
    }

    //学生修改
    @PostMapping("/edit")
    @ResponseBody
    public Map<String,String> edit(Student student){

        Map<String,String> ret = new HashMap<String, String>();

        if (StringUtils.isEmpty(student.getUsername())){
            ret.put("type","error");
            ret.put("msg","学生姓名不能为空！");
            return ret;
        }
        if (StringUtils.isEmpty(student.getPassword())){
            ret.put("type","error");
            ret.put("msg","登录密码不能为空！");
            return ret;
        }

        if (isExit(student.getUsername(),student.getId())) {
            ret.put("type","error");
            ret.put("msg","用户已存在！");
            return ret;
        }

        if (student.getClazzId() == 0L){
            ret.put("type","error");
            ret.put("msg","请选择所属班级！");
            return ret;
        }

        student.setSn(StringUtil.generateSn("S",""));
        if (studentService.edit(student) <= 0){
            ret.put("type","error");
            ret.put("msg","修改失败！");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","修改成功");
        return ret;
    }

    //删除学生信息
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
            if (studentService.delete(str) <= 0){
                ret.put("type","error");
                ret.put("msg","删除失败！");
                return ret;
            }
        }catch (Exception e){
            ret.put("type","error");
            ret.put("msg","该学生存在其它关联信息信息，不能删除！");
            return ret;
        }

        ret.put("type","success");
        ret.put("msg","删除成功");
        return ret;
    }

    private boolean isExit(String username,long id){

        Student student = studentService.findUserByUserName(username);
        if (student != null) {
            if (id == 0L) {
                return true;
            }
//            if (student.getId().longValue() != id.longValue()) {
//                return true;
//            }
        }
        return false;
    }
}