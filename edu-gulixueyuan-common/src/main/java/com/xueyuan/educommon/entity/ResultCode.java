package com.xueyuan.educommon.entity;

public enum ResultCode {

    OK(20000,"成功"),
    ERROR(20001,"失败"),
    LOGIN_ERROR(20002,"用户名或密码错误"),
    ACCESS_ERROR(20003,"权限不足"),
    REMOTE_ERROR(20004,"远程调用失败"),
    REPEAT_ERROR(20005,"重复操作");

    private Integer code;
    private String message;

    private ResultCode(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
