package com.jafir.springboot.service.model.api;

/**
 * Created by jafir on 2020/6/4.
 */
public class ResponseUtil {

    public final static String SUCCESS = "请求成功";
    public final static String FAIL = "请求失败";
    public final static String FAIL401 = "token不存在或者已过期";
    public final static String FAIL404 = "请求不存在";
    public final static String FAIL500 = "服务器错误";


    public static <T> ResponseResult<T> makeRsp(int code, String msg) {
        return new ResponseResult<T>().setCode(code).setMsg(msg);
    }

    public static <T> ResponseResult<T> makeRsp(int code, String msg, T data) {
        return new ResponseResult<T>().setCode(code).setMsg(msg).setData(data);
    }

    public static <T> ResponseResult<T> makeOK() {
        return new ResponseResult<T>().setCode(ResultCode.SUCCESS).setMsg(SUCCESS);
    }

    public static <T> ResponseResult<T> makeOK(String message) {
        return new ResponseResult<T>().setCode(ResultCode.SUCCESS).setMsg(message);
    }

    public static <T> ResponseResult<T> makeOK(T data) {
        return new ResponseResult<T>().setCode(ResultCode.SUCCESS).setMsg(SUCCESS).setData(data);
    }

    /**
     *  *******************  error  **********************
     */

    public static <T> ResponseResult<T> makeErr(String message) {
        return new ResponseResult<T>().setCode(ResultCode.FAIL).setMsg(message);
    }

    public static <T> ResponseResult<T> makeErr() {
        return new ResponseResult<T>().setCode(ResultCode.FAIL).setMsg(FAIL);
    }

    public static <T> ResponseResult<T> make401Err() {
        return new ResponseResult<T>().setCode(ResultCode.UNAUTHORIZED).setMsg(FAIL401);
    }

    public static <T> ResponseResult<T> make404Err() {
        return new ResponseResult<T>().setCode(ResultCode.NOT_FOUND).setMsg(FAIL404);
    }

    public static <T> ResponseResult<T> make500Err(String message) {
        return new ResponseResult<T>().setCode(ResultCode.INTERNAL_SERVER_ERROR).setMsg(message);
    }

    public static <T> ResponseResult<T> make500Err() {
        return new ResponseResult<T>().setCode(ResultCode.INTERNAL_SERVER_ERROR).setMsg(FAIL500);
    }
}
