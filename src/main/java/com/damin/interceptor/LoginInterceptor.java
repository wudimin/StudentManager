package com.damin.interceptor;

import com.damin.entity.User;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        //System.out.println("进行了"+requestURI+"请求");

        Object user = request.getSession().getAttribute("user");
        if (user == null){
            System.out.println("未登录或登录失效，请重新登录！");
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
                Map<String,String> ret = new HashMap<String, String>();
                ret.put("type","error");
                ret.put("msg","登录失效，请重新登录！");
                response.getWriter().write(JSONObject.fromObject(ret).toString());
                return false;
            }

            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }

        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}