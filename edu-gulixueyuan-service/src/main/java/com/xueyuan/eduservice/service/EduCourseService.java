package com.xueyuan.eduservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyuan.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyuan.eduservice.entity.dto.CourseDto;
import com.xueyuan.eduservice.entity.front.CourseFront;
import com.xueyuan.eduservice.entity.info.PublishCourseInfo;
import com.xueyuan.eduservice.entity.query.CourseQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-07-09
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加课程和课程描述
    String saveCourseAndDesription(CourseDto courseDto);

    //通过课程id获取课程信息
    CourseDto getCourseById(String courseId);

    //通过id修改课程
    void updateCourse(CourseDto courseDto);

    //通过课程id获取封装的课程信息
    PublishCourseInfo getPublishCourseInfo(String courseId);

    //分页进行条件查询
    void getCoursePageByQuery(Page<EduCourse> page1, CourseQuery courseQuery);

    void deleteCourseById(String courseId);

    List<EduCourse> getCourseByTeacherId(String teacherId);

    Map<String,Object> getCoursePage(Page<EduCourse> page1);


    //获取课程详细信息
    CourseFront getCourseSourse(String courseId);

    //更新课程浏览数
    void updateCourseView(String courseId);


}
