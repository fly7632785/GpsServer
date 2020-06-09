package com.jafir.springboot.service.dao;

import com.jafir.springboot.service.model.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> getUsers();

    User getUserByName(String username);
}