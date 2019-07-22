package com.xueyuan.educenter.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.xueyuan.educenter.mapper")
@Configuration
@EnableTransactionManagement
@ComponentScan("com.xueyuan.educommon.handler")
//@ComponentScan("com.xueyuan.educommon.handler")
public class UcenterConfig {



}
