package com.jafir.springboot.interceptor;

import com.alibaba.fastjson.JSON;
import com.jafir.springboot.service.model.api.ResponseResult;
import com.jafir.springboot.service.model.api.ResponseUtil;
import com.jafir.springboot.service.model.api.ResultCode;
import com.jafir.springboot.util.JwtUtil;
import com.jafir.springboot.util.LogUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by jafir on 2020/6/4.
 */
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LogUtil.info("url:" + request.getRequestURI());

        //获取所有注册的url  如果不包含的url 不拦截 最终走向404
//        if (!ApplicationContextUtil.getAllUrl().contains(request.getRequestURI())) {
//            return true;
//        }


        String token = request.getHeader("token");
        LogUtil.info("token:" + token);
        //token不存在
        if (null != token) {
            //验证token是否正确
            boolean result = JwtUtil.verify(token);
            if (result) {
                return true;
            }
        }
        ResponseResult<Object> responseResult = ResponseUtil.makeRsp(ResultCode.UNAUTHORIZED.code, ResponseUtil.INVALID_TOKEN);
        responseMessage(response, response.getWriter(), responseResult);
        return false;
    }

    /**
     * 返回信息给客户端
     *
     * @param response
     * @param out
     * @param apiResponse
     */
    private void responseMessage(HttpServletResponse response, PrintWriter out, ResponseResult apiResponse) {
        response.setContentType("application/json; charset=utf-8");
        out.print(JSON.toJSON(apiResponse));
        out.flush();
        out.close();
    }
}
