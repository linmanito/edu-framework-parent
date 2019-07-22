package com.xueyuan.educommon.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor //带参构造器
@NoArgsConstructor //无参构造器
@Data
public class ServiceException extends RuntimeException {


    private Integer code;


    private String message;



}
