package com.xueyuan.eduservice.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyuan.educommon.entity.R;
import com.xueyuan.eduservice.entity.EduCourse;
import com.xueyuan.eduservice.entity.dto.ChapterDto;
import com.xueyuan.eduservice.entity.front.CourseFront;
import com.xueyuan.eduservice.service.EduChapterService;
import com.xueyuan.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(description = "前台课程管理")
@CrossOrigin
@RequestMapping("/eduservice/front-course")
@RestController
public class FrontCourseController {

    @Autowired
    EduCourseService courseService;

    @Autowired
    EduChapterService chapterService;


    @ApiOperation(value = "获取课程列表")
    @GetMapping("getCourses/{page}/{limit}")
    public R getCourses(@PathVariable Long page,
                            @PathVariable Long limit){

        Page<EduCourse> page1 = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCoursePage(page1);

        return R.ok().data(map);
    }



    @ApiOperation(value = "课程详细信息")
    @GetMapping("getCourseSourse/{courseId}")
    public R getCourseSourse(@PathVariable String courseId){

        CourseFront courseFront = courseService.getCourseSourse(courseId);

        List<ChapterDto> chapterList = chapterService.getAllChapterList(courseId);


        return R.ok().data("course",courseFront).data("chapterList",chapterList);

    }






}
