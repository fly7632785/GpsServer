package com.jafir.springboot.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jafir on 2020/6/4.
 */
public class ProcessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        String origin = httpServletRequest.getHeader("Origin");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        // 是否允许浏览器携带用户身份信息（cookie）,设置为true,必须设置域名,不能使用通配符
//        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");

//        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With");

//        httpServletResponse.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        String method = httpServletRequest.getMethod();
        return true;
    }
}
