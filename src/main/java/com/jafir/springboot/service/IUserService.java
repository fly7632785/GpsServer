package com.jafir.springboot.service;

import com.jafir.springboot.service.model.User;
import com.jafir.springboot.service.model.result.AllUserResult;

import java.util.List;

/**
 * Created by jafir on 2018/3/7.
 */
public interface IUserService extends IBaseService {

    List<AllUserResult> getUsers();

    User getUserByName(String username);

    Integer createUser(User user);

    boolean checkUser(String username, String password);

    Integer updateUser(User user);

    void deleteUser(Long uid);

    User getUserById(Long uid);
}
