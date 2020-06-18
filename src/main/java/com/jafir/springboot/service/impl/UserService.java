package com.jafir.springboot.service.impl;

import com.jafir.springboot.service.IUserService;
import com.jafir.springboot.service.dao.UserMapper;
import com.jafir.springboot.service.model.User;
import com.jafir.springboot.service.model.result.AllUserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jafir on 2018/3/7.
 */
@Service
public class UserService extends BaseService implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<AllUserResult> getUsers() {
        return userMapper.getUsers();
    }

    @Override
    public User getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

    @Override
    public Integer createUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public boolean checkUser(String username, String password) {
        User user = userMapper.getUserByName(username);
        if (user != null) {
            if (password.equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void deleteUser(Long uid) {
        userMapper.deleteByPrimaryKey(uid);
    }

    @Override
    public User getUserById(Long uid) {
        return userMapper.selectByPrimaryKey(uid);
    }
}
