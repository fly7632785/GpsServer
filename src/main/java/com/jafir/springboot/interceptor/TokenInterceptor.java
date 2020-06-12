package com.jafir.springboot.interceptor;

import com.jafir.springboot.exception.TokenException;
import com.jafir.springboot.util.JwtUtil;
import com.jafir.springboot.util.LogUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jafir on 2020/6/4.
 */
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LogUtil.info("url:" + request.getRequestURI());

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            LogUtil.info("OPTIONS:" + request.getRequestURI());
            return true;
        }

        //获取所有注册的url  如果不包含的url 不拦截 最终走向404

//        if (!ApplicationContextUtil.getAllUrl().contains(request.getRequestURI())) {
//            return true;
//        }

        LogUtil.info("handler:" + handler);
//        if(handler instanceof ResourceHttpRequestHandler){
//           return true;
//        }


        String token = request.getHeader("token");
        LogUtil.info("token:" + token);
        if (null != token) {
            boolean result = JwtUtil.verify(token);
            if (result) {
                //存在且正确 不拦截
                return true;
            }
        }
        //不存在或者错误抛异常
        throw new TokenException();
    }

}
