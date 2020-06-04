package com.jafir.springboot.service.impl;

import com.jafir.springboot.service.IUserService;
import com.jafir.springboot.service.dao.IUserDao;
import com.jafir.springboot.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jafir on 2018/3/7.
 */
@Service
public class UserService extends BaseService implements IUserService {
    @Autowired
    IUserDao userDao;

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public User getUserByName(String username) {
        return userDao.getUserByName(username);
    }

    @Override
    public Integer createUser(User user) {
        return userDao.createUser(user);
    }

    @Override
    public boolean checkUser(String username, String password) {
        User user = userDao.getUserByName(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public void deleteUser(String uid) {
        userDao.deleteUser(uid);
    }
}
