package com.damin.controller;

import com.damin.entity.Student;
import com.damin.entity.User;
import com.damin.service.StudentService;
import com.damin.service.UserService;
import com.damin.service.impl.UserServiceImpl;
import com.damin.util.CpachaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SystemController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    //环境测试
//    @RequestMapping(value = "/hello",method = RequestMethod.GET)
//    public String hello(Model model){
//        model.addAttribute("msg","Hello World!");
//        return "index";
//    }

    @GetMapping("/index")
    public ModelAndView hello(ModelAndView model){
        model.setViewName("/index");
        return model;
    }

    //系统登录
    @GetMapping("/login")
    public ModelAndView login(ModelAndView model){
        model.setViewName("/login");
        return model;
    }

    //注销登录
    @GetMapping("/login_out")
    public String loginOut(HttpServletRequest request) {
        request.getSession().setAttribute("user",null);

        return "redirect:login";
    }


    //表单数据提交
    //@PostMapping("/login")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String>login(@RequestParam(value = "username",required = true) String username,
                                   @RequestParam(value = "password",required = true) String password,
                                   @RequestParam(value = "vcode",required = true) String vcode,
                                   @RequestParam(value = "type",required = true) int type,
                                   HttpServletRequest request){
        Map<String,String> ret = new HashMap<String, String>();
        if(StringUtils.isEmpty(username)){
            ret.put("type","error");
            ret.put("msg","用户名不能为空!");
            return ret;
        }
        if(StringUtils.isEmpty(password)){
            ret.put("type","error");
            ret.put("msg","密码不能为空!");
            return ret;
        }
        if(StringUtils.isEmpty(vcode)){
            ret.put("type","error");
            ret.put("msg","验证码不能为空!");
            return ret;
        }
        String loginCpacha = (String) request.getSession().getAttribute("loginCpacha");
        if(StringUtils.isEmpty(loginCpacha)){
            ret.put("type","error");
            ret.put("msg","长时间没操作，请重新输入!");
            return ret;
        }
        if (!vcode.toUpperCase().equals(loginCpacha.toUpperCase())){
            ret.put("type","error");
            ret.put("msg","验证码错误!");
            return ret;
        }

        // request.getSession().setAttribute("user",user);

        //从数据库中查新信息
        if (type == 1){
            User user = userService.findUserByUserName(username);
            if (user == null){
                ret.put("type","error");
                ret.put("msg","用户不存在!");
                return ret;
            }
            if (!password.equals(user.getPassword())){
                ret.put("type","error");
                ret.put("msg","密码错误!");
                return ret;
            }
            request.getSession().setAttribute("user",user);//取出用户名在页面中可以使用
        }


        if (type == 2){
            //学生信息
            Student student = studentService.findUserByUserName(username);
            if (student == null){
                ret.put("type","error");
                ret.put("msg","该学生不存在!");
                return ret;
            }
            if (!password.equals(student.getPassword())){
                ret.put("type","error");
                ret.put("msg","密码错误!");
                return ret;
            }

            request.getSession().setAttribute("user", student);
        }

        request.getSession().setAttribute("userType", type);
        ret.put("type","success");
        ret.put("msg","登录成功!");
        return ret;
    }

    //验证码显示
    @GetMapping("/get_cpacha")
    public void getCpacha(HttpServletRequest request,
                          @RequestParam(value = "vl",defaultValue = "4",required = false) Integer vl,
                          @RequestParam(value = "w",defaultValue = "98",required = false) Integer w,
                          @RequestParam(value = "h",defaultValue = "33",required = false) Integer h,
                          HttpServletResponse response) throws IOException {

        //System.out.println("获取验证码");
        CpachaUtil cpachaUtil = new CpachaUtil(vl,w,h);
        String generatorVCode = cpachaUtil.generatorVCode();
        request.getSession().setAttribute("loginCpacha",generatorVCode);
        BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
        ImageIO.write(generatorRotateVCodeImage,"gif",response.getOutputStream());

    }

}
