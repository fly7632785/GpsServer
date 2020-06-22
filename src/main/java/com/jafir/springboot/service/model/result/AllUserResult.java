package com.jafir.springboot.service.model.result;

import lombok.Data;

/**
 * Created by jafir on 2020/6/16.
 */
@Data
public class AllUserResult {
    private Long uid;
    private String name;
    private String avatar;
    private String username;
    private String mobile;
    private Long lastLoginTime;
    private int  role;
}
