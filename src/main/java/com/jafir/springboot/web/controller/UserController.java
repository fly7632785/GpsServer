package com.jafir.springboot.web.controller;

import com.jafir.springboot.service.IUserServiceI;
import com.jafir.springboot.service.model.api.ResponseResult;
import com.jafir.springboot.service.model.api.ResponseUtil;
import com.jafir.springboot.service.model.User;
import com.jafir.springboot.service.model.result.LoginResult;
import com.jafir.springboot.util.JwtUtil;
import com.jafir.springboot.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by jafir on 2018/3/7.
 */
@Controller
public class UserController extends BaseController {

    @Autowired
    private IUserServiceI userService;


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
            return ResponseUtil.makeOK(new LoginResult(token, user));
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
