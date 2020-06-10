package com.jafir.springboot.exception;

import lombok.Data;

/**
 * Created by jafir on 2020/6/10.
 */
@Data
public class BusinessException extends RuntimeException {
    private String msg;
}
