package com.hg.Interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("interceptor.......");
        //判断病人是否已登录
        if(request.getSession().getAttribute("patient1") != null){
            return true;
        }
        //获取请求类型
        String type = request.getHeader("X-Requested-with")==null?"":request.getHeader("X-Requested-with");
        if(StringUtils.equals("XMLHttpRequest",type)){
            //xml类型，ajax，返回信息给ajax，在响应头中设置一个参数，让ajax来跳转
            response.setHeader("SESSIONSTATUS","TIMEOUT");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }else {
            //一般类型呢，form表单，a标签
            response.sendRedirect(request.getContextPath()+"/login.html");
        }
        //未登录跳转到登录界面
        //request.getRequestDispatcher("/login.html").forward(request,response);
        return false;
    }

}
