package com.jafir.springboot.service.model.result;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by jafir on 2020/6/4.
 */
@Data
@AllArgsConstructor
public class LoginResult {
    private String token;
    private String uid;
}
