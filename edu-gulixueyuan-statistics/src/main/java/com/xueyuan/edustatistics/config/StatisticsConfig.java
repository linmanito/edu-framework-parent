package com.xueyuan.edustatistics.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan("com.xueyuan.edustatistics.mapper")
@ComponentScan("com.xueyuan.educommon.handler")
public class StatisticsConfig {
}
