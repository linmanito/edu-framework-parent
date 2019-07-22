package com.xueyuan.eduservice.controller;


import com.xueyuan.educommon.entity.R;
import com.xueyuan.eduservice.entity.EduChapter;
import com.xueyuan.eduservice.entity.EduVideo;
import com.xueyuan.eduservice.entity.dto.ChapterDto;
import com.xueyuan.eduservice.entity.dto.VideoDto;
import com.xueyuan.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-07-11
 */
@RestController
@RequestMapping("/eduservice/edu-chapter")
@Api(description = "课程章节管理")
@CrossOrigin
public class EduChapterController {

    @Autowired
    EduChapterService chapterService;

    @ApiOperation(value = "获取所有章节信息")
    @GetMapping("chapterList/{courseId}")
    public R getChapterAllList(@PathVariable String courseId){

        List<ChapterDto> chapterList = chapterService.getAllChapterList(courseId);

        return R.ok().data("list",chapterList);
    }

    @ApiOperation(value = "添加章节")
    @PostMapping("saveChapter")
    public R saveChapter(@RequestBody EduChapter eduChapter){

        chapterService.saveChapter(eduChapter);

        return R.ok();
    }




    @ApiOperation(value = "删除章节")
    @DeleteMapping("delete/{id}")
    public R deleteChapterById(@PathVariable String id){

        boolean flag = chapterService.deleteChapter(id);

        return R.ok();

    }


    @ApiOperation(value = "根据章节id获取章节信息")
    @GetMapping("getChapterById/{id}")
    public R getChapterById(@PathVariable String id){

        ChapterDto chapterDto = chapterService.getChapterById(id);

        return R.ok().data("chapter",chapterDto);
    }


    @ApiOperation(value = "修改章节信息")
    @PutMapping("getChapterById")
    public R updateChapter(@RequestBody EduChapter chapter){

        boolean flag = chapterService.updateChapter(chapter);

        return R.ok();
    }




}

