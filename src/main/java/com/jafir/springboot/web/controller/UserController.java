package com.jafir.springboot.web.controller;

import com.jafir.springboot.service.IUserService;
import com.jafir.springboot.service.model.User;
import com.jafir.springboot.service.model.api.ResponseResult;
import com.jafir.springboot.service.model.api.ResponseUtil;
import com.jafir.springboot.service.model.result.LoginResult;
import com.jafir.springboot.util.JwtUtil;
import com.jafir.springboot.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jafir on 2018/3/7.
 */
@Controller
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @RequestMapping("getAllUrl")
    @ResponseBody
    public Set<String> getAllUrl(HttpServletRequest request) {
        Set<String> result = new HashSet<String>();
        WebApplicationContext wc = (WebApplicationContext) request.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        RequestMappingHandlerMapping bean = wc.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = bean.getHandlerMethods();
        for (RequestMappingInfo rmi : handlerMethods.keySet()) {
            PatternsRequestCondition pc = rmi.getPatternsCondition();
            Set<String> pSet = pc.getPatterns();
            result.addAll(pSet);
        }
        return result;
    }


    @RequestMapping(value = "/getusers", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUsers() {
        return userService.getUsers();
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<LoginResult> login(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        boolean result = userService.checkUser(username, password);
        if (result) {
            User user = userService.getUserByName(username);
            String token = JwtUtil.sign(username, password);
            user.setToken(token);
            userService.updateUser(user);
            return ResponseUtil.makeOK(new LoginResult(token, user.getUid().toString()));
        }
        return ResponseUtil.make400Err(ResponseUtil.LOGIN_FAIL);
    }


    @RequestMapping(value = "/get_info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<LoginResult> getUserInfo(@RequestParam Long uid) {
        User user = userService.getUserById(uid);
        if (user != null) {
            return ResponseUtil.makeOK();
        }
        return ResponseUtil.make400Err(ResponseUtil.LOGIN_FAIL);
    }


    @RequestMapping(value = "/create_user", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<User> createUser(@RequestBody User user) {
        user.setCreateTime(System.currentTimeMillis());
        User userByName = userService.getUserByName(user.getUsername());
        if (userByName != null) {
            return ResponseUtil.make400Err("用户已经注册");
        }
        userService.createUser(user);
        return ResponseUtil.makeOK(user);
    }

    @RequestMapping(value = "/update_user", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<User> updateUser(@RequestBody User user) {
        if (user.getUid() == null) {
            return ResponseUtil.make400Err("userId不能为空");
        }
        user.setUpdateTime(System.currentTimeMillis());
        userService.updateUser(user);
        LogUtil.info("user1" + user);
        return ResponseUtil.makeOK(user);
    }

    @RequestMapping(value = "/delete_user", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<User> deleteUser(@RequestParam String uid) {
        userService.deleteUser(Long.valueOf(uid));
        return ResponseUtil.makeOK();
    }
}
