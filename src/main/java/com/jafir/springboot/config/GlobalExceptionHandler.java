package com.jafir.springboot.config;

import com.jafir.springboot.exception.BusinessException;
import com.jafir.springboot.exception.TokenException;
import com.jafir.springboot.service.model.api.ResponseResult;
import com.jafir.springboot.service.model.api.ResponseUtil;
import com.jafir.springboot.util.LogUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jafir on 2020/6/10.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 系统异常处理
     * 系统错误 500
     * 业务错误 400
     * 请求不存在 404
     * token失效 401
     *
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseResult defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        LogUtil.error(e.toString());
        // todo 写入日志

        ResponseResult responseResult;
        if (e instanceof BusinessException) {
            responseResult = ResponseUtil.makeErr(((BusinessException) e).getMsg());
        } else if (e instanceof TokenException) {
            responseResult = ResponseUtil.make401Err();
        } else if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            responseResult = ResponseUtil.make404Err();
        } else {
            responseResult = ResponseUtil.make500Err();
        }
        return responseResult;
    }
}