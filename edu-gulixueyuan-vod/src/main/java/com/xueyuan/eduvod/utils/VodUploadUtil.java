package com.xueyuan.eduvod.utils;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
//@PropertySource("application.properties")//该注解是为了指定配置文件，一般是在有多个配置文件的时候使用
public class VodUploadUtil implements InitializingBean{//为了能够在服务器刚创建就和服务器一起被创建

    @Value("${aliyun.vod.file.keyid}")
    private String keyid ;

    @Value("${aliyun.vod.file.keysecret}")
    private String keysecret;

    public static String KEY_ID;

    public static String KEY_SECRET;


    @Override
    public void afterPropertiesSet() throws Exception {
        KEY_ID = keyid;
        KEY_SECRET = keysecret;
    }
}
