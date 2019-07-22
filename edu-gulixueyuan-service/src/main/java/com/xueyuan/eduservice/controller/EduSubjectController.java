package com.xueyuan.eduservice.controller;


import com.xueyuan.educommon.entity.R;
import com.xueyuan.eduservice.entity.EduSubject;
import com.xueyuan.eduservice.entity.dto.OneSubjectDto;
import com.xueyuan.eduservice.entity.dto.TwoSubjectDto;
import com.xueyuan.eduservice.service.EduSubjectService;
import com.xueyuan.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-07-08
 */
@RestController
@Api(description = "课程分类")
@RequestMapping("/eduservice/edu-subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    EduSubjectService eduSubjectService;


    @ApiOperation(value = "通过map作为缓存将dto添加至数据库")
    @PostMapping("getSubjectByMap")
    public R getSubjectByMap(){

        List<OneSubjectDto> oneSubjectDtos = eduSubjectService.subjectTreeListByMap();

        return R.ok().data("subjectMap",oneSubjectDtos);
    }


    @ApiOperation(value = "传入Excel文件通过Poi添加至数据库")
    @PostMapping("import")
    public R saveSubByPoi(MultipartFile file){

        List<String> msg = eduSubjectService.saveSubjectByPoi(file);

        if (msg.size() == 0){
            return R.ok().message("文件上传成功");
        }else {
            return R.error().message("部分数据上传成功").data("msg",msg);
        }



    }

//    @GetMapping("list")
//    public R getSubjectList(){
//
//        List<OneSubjectDto> list = eduSubjectService.subjectTreeList();
//
//        return R.ok().data("list",list);
//
//    }


    @ApiOperation(value = "课程管理列表树形结构")
    @GetMapping("tree")
    public R getSubjectTree(){

        List<OneSubjectDto> list = eduSubjectService.subjectTreeList();

        return R.ok().data("tree",list);
    }


    @DeleteMapping("deleteSubject/{id}")
    public R deleteSubject(@PathVariable String id){

        boolean b = eduSubjectService.deleteSubjectById(id);

        if (b == true){
            return R.ok().message("删除成功");
        }else {
            return R.error().message("删除失败");
        }

    }


    //添加一级分类
    @PostMapping("saveOneLevel")
    public R saveOneLevel(@RequestBody EduSubject subject){

        boolean b = eduSubjectService.saveOneSubeject(subject);
        if (b == true){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //添加二级分类
    @PostMapping("saveTwoLevel")
    public R saveTwoLevel(@RequestBody EduSubject subject){

        boolean b = eduSubjectService.saveTwoSubject(subject);

        if (b == true){
            return R.ok();
        }else {
            return R.error();
        }

    }

}

