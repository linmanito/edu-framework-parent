package com.xueyuan.educommon.handler;


import com.xueyuan.educommon.entity.R;

import com.xueyuan.educommon.exception.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice//该注解是为了标注为处理异常的
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public R error(ServiceException e){
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMessage());
    }
}
