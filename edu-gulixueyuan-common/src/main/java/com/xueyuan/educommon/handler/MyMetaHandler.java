package com.xueyuan.educommon.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xueyuan.educommon.entity.R;
import com.xueyuan.educommon.exception.ServiceException;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Component
public class MyMetaHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("gmtCreate" ,new Date() ,metaObject);
        this.setFieldValByName("gmtModified" ,new Date() ,metaObject);
    }



    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified" ,new Date() ,metaObject);
    }
}
