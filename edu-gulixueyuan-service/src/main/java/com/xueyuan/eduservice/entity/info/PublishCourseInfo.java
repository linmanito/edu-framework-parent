package com.xueyuan.eduservice.entity.info;

import lombok.Data;

@Data
public class PublishCourseInfo {

    private String id;
    private String title;//课程名称
    private String cover;//课程封面
    private Integer lessonNum;//课时数
    private String subjectLevelOne;//一级分类
    private String subjectLevelTwo;//二级分类
    private String teacherName;//教师名称
    private String price;//只用于显示

}
