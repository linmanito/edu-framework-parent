package com.xueyuan.educommon.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R {

    private boolean success;//是否成功

    private Integer code;//响应码

    private String message;//响应信息

    private Map<String,Object> data = new HashMap<>();//返回数据

    private R(){}//构造器私有化，防止new对象

    public static R ok(){

        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.OK.getCode());
        r.setMessage(ResultCode.OK.getMessage());

        return r;

    }

    public static R error(){

        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR.getCode());
        r.setMessage(ResultCode.ERROR.getMessage());

        return r;
    }

    public R code(Integer code){

        this.setCode(code);

        return this;//链式编程

    }

    public R message(String message){
        this.setMessage(message);

        return this;
    }

    public R data(Map<String,Object> data){
        this.setData(data);

        return this;
    }

    public R data(String key,Object value){

        this.data.put(key ,value);

        return this;

    }


}
