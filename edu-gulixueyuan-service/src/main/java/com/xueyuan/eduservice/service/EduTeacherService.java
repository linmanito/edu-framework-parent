package com.xueyuan.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyuan.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyuan.eduservice.entity.query.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-07-06
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void pageQuery(Page<EduTeacher> page, TeacherQuery teacherQuery);

    Map<String,Object> getListTeacherFront(Page<EduTeacher> pageTeacher);

}
