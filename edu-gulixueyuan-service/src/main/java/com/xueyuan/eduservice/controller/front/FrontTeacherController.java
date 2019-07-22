package com.xueyuan.eduservice.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyuan.educommon.entity.R;
import com.xueyuan.eduservice.entity.EduCourse;
import com.xueyuan.eduservice.entity.EduTeacher;
import com.xueyuan.eduservice.service.EduCourseService;
import com.xueyuan.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(description = "前台讲师管理")
@RestController
@RequestMapping("/eduservice/frontTeacher")
@CrossOrigin
public class FrontTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @ApiOperation(value = "讲师列表分页")
    @GetMapping("getFrontTeacherList/{page}/{limit}")
    public R getFrontTeacherList(@PathVariable Long page,
                                    @PathVariable Long limit){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);

        //把分页数据都放入map中
        Map<String,Object> map = teacherService.getListTeacherFront(pageTeacher);



        return R.ok().data(map);
    }


    @ApiOperation(value = "获取讲师详细信息")
    @GetMapping("getTeacher/{id}")
    public R getTeacherById(@PathVariable String id){

        EduTeacher teacher = teacherService.getById(id);

        List<EduCourse> courseList = courseService.getCourseByTeacherId(id);

        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }








}
