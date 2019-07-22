package com.xueyuan.eduvod;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//配置为主程序，主启动项，后面加的
//注解是为了排除数据库资源的class类
@EnableEurekaClient//放入注册中心
public class VodApplication {

    public static void main(String[] args) {

        SpringApplication.run(VodApplication.class, args);
    }

}
