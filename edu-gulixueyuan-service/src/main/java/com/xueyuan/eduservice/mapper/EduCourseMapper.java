package com.xueyuan.eduservice.mapper;

import com.xueyuan.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyuan.eduservice.entity.front.CourseFront;
import com.xueyuan.eduservice.entity.info.PublishCourseInfo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2019-07-09
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public PublishCourseInfo getPublishCourseInfo(String courseId);

    CourseFront getCourseFront(String courseId);

}
