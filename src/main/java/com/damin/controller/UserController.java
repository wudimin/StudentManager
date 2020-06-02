package com.damin.controller;

import com.damin.entity.User;
import com.damin.page.Page;
import com.damin.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    //用户管理界面
    @GetMapping("/list")
    public ModelAndView list(ModelAndView model){
        model.setViewName("user/user_list");
        return model;
    }

    //显示用户列表
    @PostMapping("/get_list")
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "username",defaultValue = "",required = false) String username,
            Page page){
        Map<String,Object> ret = new HashMap<String, Object>();
        Map<String,Object> querMap = new HashMap<String, Object>();
        querMap.put("username","%"+username+"%");
        //分页
        querMap.put("offset",page.getOffset());
        querMap.put("pageSize",page.getRows());
        ret.put("rows",userService.findList(querMap));
        ret.put("total",userService.getTotal(querMap));
        return ret;
    }

    //用户添加
    @PostMapping("/add")
    @ResponseBody
    public Map<String,String> add(User user){

        Map<String,String> ret = new HashMap<String, String>();
        if (user == null){
            ret.put("type","error");
            ret.put("msg","请联系系统开发人员！");
            return ret;
        }
        if (StringUtils.isEmpty(user.getUsername())){
            ret.put("type","error");
            ret.put("msg","用户名不能为空！");
            return ret;
        }
        if (StringUtils.isEmpty(user.getPassword())){
            ret.put("type","error");
            ret.put("msg","密码不能为空！");
            return ret;
        }
        //查询数据库，保证用户名唯一
        User exitUser = userService.findUserByUserName(user.getUsername());
        if (exitUser != null){
            ret.put("type","error");
            ret.put("msg","用户已存在！");
            return ret;
        }
        if (userService.add(user) <= 0){
            ret.put("type","error");
            ret.put("msg","添加失败！");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","添加成功");
        return ret;
    }

    //用户修改
    @PostMapping("/edit")
    @ResponseBody
    public Map<String,String> edit(User user){

        Map<String,String> ret = new HashMap<String, String>();
        if (user == null){
            ret.put("type","error");
            ret.put("msg","请联系系统开发人员！");
            return ret;
        }
        if (StringUtils.isEmpty(user.getUsername())){
            ret.put("type","error");
            ret.put("msg","用户名不能为空！");
            return ret;
        }
        if (StringUtils.isEmpty(user.getPassword())){
            ret.put("type","error");
            ret.put("msg","密码不能为空！");
            return ret;
        }
        //查询数据库，保证用户名唯一
        User exitUser = userService.findUserByUserName(user.getUsername());
        if (exitUser != null){
            if (exitUser.getId() != user.getId()){
                ret.put("type","error");
                ret.put("msg","用户已存在！");
                return ret;
            }
        }
        if (userService.edit(user) <= 0){
            ret.put("type","error");
            ret.put("msg","修改失败！");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","修改成功");
        return ret;
    }

    //删除用户
    @PostMapping("/delete")
    @ResponseBody
    public Map<String,String> delete(@RequestParam(value = "ids[]",required = true) long[] ids) {

        Map<String, String> ret = new HashMap<String, String>();
        if (ids == null){
            ret.put("type","error");
            ret.put("msg","您还没有选择任何数据哦！");
            return ret;
        }
        String str = "";
        for (long id : ids) {
            str += id + ",";
        }
        str = str.substring(0,str.length()-1);
        if (userService.delete(str) <= 0){
            ret.put("type","error");
            ret.put("msg","删除失败！");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","删除成功");
        return ret;
    }
}
