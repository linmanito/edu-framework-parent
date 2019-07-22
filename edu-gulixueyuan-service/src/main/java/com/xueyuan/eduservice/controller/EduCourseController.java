package com.xueyuan.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyuan.educommon.entity.R;
import com.xueyuan.eduservice.entity.EduCourse;
import com.xueyuan.eduservice.entity.dto.CourseDto;
import com.xueyuan.eduservice.entity.info.PublishCourseInfo;
import com.xueyuan.eduservice.entity.query.CourseQuery;
import com.xueyuan.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-07-09
 */
@RestController
@CrossOrigin
@Api(description = "课程详细信息")
@RequestMapping("/eduservice/edu-course")
public class EduCourseController {


    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation(value = "修改课程信息")
    @PostMapping("update")
    public R updateCourse(@RequestBody CourseDto courseDto){

        eduCourseService.updateCourse(courseDto);

        return R.ok();
    }


    @PostMapping("save")
    @ApiOperation(value = "添加课程信息")
    public R saveCourse(@RequestBody CourseDto courseDto){

        String courseId = eduCourseService.saveCourseAndDesription(courseDto);

        if (!StringUtils.isEmpty(courseId)){
            return R.ok().data("courseId",courseId);
        }else {
            return R.error();

        }

    }


    @ApiOperation(value = "回显获取课程信息")
    @GetMapping("getCourseInfoById/{courseId}")
    public R getCourseInfoById(@PathVariable String courseId){
        System.out.println(courseId);
        CourseDto courseDto = eduCourseService.getCourseById(courseId);

        return R.ok().data("courseInfo",courseDto);

    }

    @ApiOperation(value = "根据课程id获取课程所有信息")
    @GetMapping("getpublishCourseInfo/{courseId}")
    public R getPublishCourseInfoById(@PathVariable String courseId){

        PublishCourseInfo publishCourseInfo = eduCourseService.getPublishCourseInfo(courseId);

        return R.ok().data("item",publishCourseInfo);

    }

    @ApiOperation(value = "修改课程状态，Normal为已发布，Draft为未发布")
    @PutMapping("finalPublishCourse/{courseId}")
    public R finalPublishCourse(@PathVariable String courseId){

        EduCourse eduCourse = new EduCourse();

        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");

        boolean b = eduCourseService.updateById(eduCourse);

        return R.ok();
    }


    @ApiOperation(value = "分页查询")
    @PostMapping("findCoursePage/{page}/{limit}")
    public R getCourseAll(
            @ApiParam(name = "page",value = "当前页")
            @PathVariable Long page,
            @ApiParam(name = "limit",value = "每页记录数")
            @PathVariable Long limit,
            @ApiParam(name = "CourseQuery",value = "查询条件")
            @RequestBody CourseQuery courseQuery){

        Page<EduCourse> page1 = new Page<>(page,limit);

        eduCourseService.getCoursePageByQuery(page1,courseQuery);

        List<EduCourse> list = page1.getRecords();//获取当前页的数据

        long total = page1.getTotal();//获取总记录数

        return R.ok().data("total",total).data("rows",list);

    }

    @ApiOperation(value = "删除课程中所有信息")
    @DeleteMapping("deleteCourseById/{courseId}")
    public R deleteCourseByCourseId(@PathVariable String courseId){

                eduCourseService.deleteCourseById(courseId);

                return R.ok();
    }


}

