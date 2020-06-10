package com.jafir.springboot.web.controller;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by jafir on 2018/3/22.
 */

public class BaseController {
    HttpServletRequest request;
    HttpServletResponse response;
    HttpSession session;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        this.request = request;
        this.response = response;
        this.session = session;
        this.request.setAttribute("basePath", getBasePath());
        this.request.setAttribute("ctxPath", request.getContextPath());
    }

    /***
     * 返回上次访问链接
     *
     * @return
     */
    public String getBackPath() {
        return request.getHeader("referer");
    }

    /***
     * 输出
     *
     * @return
     * @throws IOException
     */
    public PrintWriter getWriter() throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        return response.getWriter();
    }

    public String getBasePath() {
        String path = request.getContextPath();
        String basePath = null;
        // log.info("访问端口号："+ request.getServerPort());
        if (request.getServerPort() != 80) {
            basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        } else {
            basePath = request.getScheme() + "://" + request.getServerName() + path;
        }
        return basePath + "/";
    }


}
