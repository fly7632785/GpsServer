package com.jafir.springboot.service.model;

import lombok.Data;

/**
 * Created by jafir on 2018/3/7.
 */
@Data
public class User {
    private Long uid;
    private String name;
    private String username;
    private String password;
    private String token;
    private Long create_time;
    private Long update_time;
    private String mobile;
    private String avatar;
    private int gender;
    private int user_role;
}
