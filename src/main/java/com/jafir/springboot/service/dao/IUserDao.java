package com.jafir.springboot.service.dao;

import com.jafir.springboot.service.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by jafir on 2018/3/7.
 */
@Mapper
public interface IUserDao extends IBaseDao {

    List<User> getUsers();

    @Select("select uid,name,username,gender,user_role,mobile,avatar from user where username = #{0}")
    User getUserByName(String username);

    Integer createUser(User user);

    @Delete("delete from user where uid = #{0}")
    void deleteUser(String uid);

    Integer updateUser(User user);
}
