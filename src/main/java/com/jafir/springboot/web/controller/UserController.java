package com.jafir.springboot.web.controller;

import com.jafir.springboot.service.IUserService;
import com.jafir.springboot.service.model.User;
import com.jafir.springboot.service.model.api.ResponseResult;
import com.jafir.springboot.service.model.api.ResponseUtil;
import com.jafir.springboot.service.model.result.AllUserResult;
import com.jafir.springboot.service.model.result.LoginResult;
import com.jafir.springboot.util.JwtUtil;
import com.jafir.springboot.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
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

    @RequestMapping("test")
    @ResponseBody
    public ResponseResult test() {
        return ResponseUtil.makeOK();
    }

    @RequestMapping(value = "/getusers", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<List<AllUserResult>> getUsers() {
        return ResponseUtil.makeOK(userService.getUsers());
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<LoginResult> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        boolean result = userService.checkUser(username, password);
        if (result) {
            User user = userService.getUserByName(username);
            String token = JwtUtil.sign(username, String.valueOf(user.getUid()));
            user.setToken(token);
            user.setLastLoginTime(System.currentTimeMillis());
            userService.updateUser(user);
            return ResponseUtil.makeOK(new LoginResult(token, user.getUid().toString()));
        }
        return ResponseUtil.makeErr("登录失败");
    }

    @RequestMapping(value = "/get_info", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseResult<User> getUserInfo(@RequestHeader(value = "token") String token) {
        LogUtil.info("token:" + token);
        String userId = JwtUtil.getUserId(token);
        LogUtil.info("userId:" + userId);
        User user = userService.getUserById(Long.valueOf(userId));
        //去掉密码
        user.setPassword("");
        if (user != null) {
            return ResponseUtil.makeOK(user);
        }
        return ResponseUtil.makeErr();
    }

    @RequestMapping(value = "/create_user", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<User> createUser(@RequestBody User user) {
        user.setCreateTime(System.currentTimeMillis());
        User userByName = userService.getUserByName(user.getUsername());
        if (userByName != null) {
            return ResponseUtil.makeErr("用户已经注册");
        }
        userService.createUser(user);
        return ResponseUtil.makeOK(user);
    }

    @RequestMapping(value = "/update_user", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<User> updateUser(@RequestHeader("token") String token, @RequestBody User user) {
        if (user.getUid() == null) {
            Long uid = Long.valueOf(JwtUtil.getUserId(token));
            user.setUid(uid);
        }
        user.setUpdateTime(System.currentTimeMillis());
        if ("".equals(user.getPassword())) {
            user.setPassword(null);
        }
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


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(MultipartFile file) throws Exception {
        System.out.print(file.getOriginalFilename());
        System.out.print(file.getSize());

        File localFile = new File("/Users/jafir/Downloads/upload", file.getOriginalFilename());
        if (!localFile.getParentFile().exists()) {
            localFile.getParentFile().mkdirs();
        }
        if (!localFile.exists()) {
            localFile.createNewFile();
        }
        file.transferTo(localFile);

        String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/res/" + localFile.getName();

        System.out.print("return url:" + returnUrl);
        return returnUrl;
    }


}
