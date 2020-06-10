package com.jafir.springboot.web.controller;

import com.jafir.springboot.service.model.api.ResponseResult;
import com.jafir.springboot.service.model.api.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jafir on 2020/6/10.
 */
@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseResult error(HttpServletRequest request, HttpServletResponse response) {
        //其他错误已经被全局处理，404无法处理，采用/error直接返回404错误的方式
        return ResponseUtil.make404Err();
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
