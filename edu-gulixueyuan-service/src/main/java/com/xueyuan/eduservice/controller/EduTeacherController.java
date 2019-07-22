package com.xueyuan.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyuan.educommon.entity.R;
import com.xueyuan.eduservice.entity.EduTeacher;
import com.xueyuan.eduservice.entity.query.TeacherQuery;
import com.xueyuan.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-07-06
 */

@CrossOrigin    //解决前后端跨域问题
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;


    @GetMapping("login")
    public R login(){
        //{"code":20000,"data":{"token":"admin"}}
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info(){
        //{"code":20000,"data":{"roles":["admin"],"name":"admin","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}}
        return R.ok().data("roles","admin").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    @ApiOperation(value = "讲师列表")
    @GetMapping("teacherList")
    public R getTeacherList(){

        List<EduTeacher> list = eduTeacherService.list(null);

        return R.ok().data("items",list);

    }


    @ApiOperation(value = "根据id逻辑删除讲师信息")
    @DeleteMapping("{id}")
    public R deleteTeacherById(@PathVariable String id){

        boolean del = eduTeacherService.removeById(id);

        return R.ok();

    }

    @ApiOperation(value = "带条件分页查询")
    @PostMapping("findTeacherPage/{page}/{limit}")
    public R getTeacherPage(
            @ApiParam(name = "page",value = "当前页",required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit",value = "每页记录数",required = true)
            @PathVariable Long limit,
            @ApiParam(name = "teacherQuery",value = "查询条件",required = false)
            @RequestBody(required = false) TeacherQuery teacherQuery
            //@RequestBody标注在对象参数上代表，会以json串的形式进行传递，使用该注解需要为post请求方式
            ){

        Page<EduTeacher> page1 = new Page<>(page ,limit);

        eduTeacherService.pageQuery(page1 ,teacherQuery);

        List<EduTeacher> records = page1.getRecords();//获得查询到的每页记录

        long total = page1.getTotal();

        return R.ok().data("total" ,total).data("rows" ,records);
    }






    @ApiOperation(value = "新增讲师")
    @PostMapping
    public R addTeacher(
                @ApiParam(name = "Teacher" ,value = "讲师对象" ,required = true)
                @RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);

        if (save){
            return R.ok();
        }else{
            return R.error();
        }
    }


    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("getTeacherById/{id}")
    public R getTeacherById(
                @ApiParam(name = "ID",value = "讲师ID")
                @PathVariable String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",teacher);

    }


    @PostMapping("updataTeacherById")
    public R updataTeacherById(@RequestBody EduTeacher eduTeacher){
        boolean update = eduTeacherService.updateById(eduTeacher);

        if (update){
            return R.ok();
        }else {
            return R.error();
        }
    }


}

