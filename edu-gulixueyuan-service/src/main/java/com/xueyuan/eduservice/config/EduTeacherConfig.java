package com.xueyuan.eduservice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;

@ComponentScan("com.xueyuan.educommon.handler")//扫描common中的R统一返回结果
@MapperScan("com.xueyuan.eduservice.mapper")//扫描mapper组件
@EnableTransactionManagement
@Configuration
public class EduTeacherConfig {

    /**
     * 逻辑删除插件
     */
    @Bean    //这个Bean注解就是将该配置类放入IOC容器管理
    public ISqlInjector sqlInjector() {
        ArrayList<Object> objects = new ArrayList<>();
        return new LogicSqlInjector();

    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


}
