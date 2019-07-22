package com.xueyuan.eduservice.controller;


import com.xueyuan.educommon.entity.R;
import com.xueyuan.eduservice.entity.EduVideo;
import com.xueyuan.eduservice.entity.dto.VideoDto;
import com.xueyuan.eduservice.entity.info.VideoInfoForm;
import com.xueyuan.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-07-11
 */
@RestController
@Api(description = "小节管理系统")
@RequestMapping("/eduservice/edu-video")
@CrossOrigin
public class EduVideoController {


    @Autowired
    EduVideoService videoService;

    @ApiOperation(value = "添加小节信息")
    @PostMapping("saveVideo")
    public R saveVideo(@RequestBody VideoInfoForm videoInfoForm){

        videoService.saveVideo(videoInfoForm);

        return R.ok();
    }


    @ApiOperation(value = "获取小节信息")
    @GetMapping("getVideoById/{id}")
    public R getVideoById(@PathVariable String id){

        VideoInfoForm videoInfoForm = videoService.getVideoById(id);

        return R.ok().data("video",videoInfoForm);
    }


    @ApiOperation(value = "修改小节信息")
    @PutMapping("updateVideo")
    public R updateVideo(@RequestBody VideoInfoForm videoInfoForm ){

        videoService.updateVideo(videoInfoForm);

        return R.ok();
    }



    @ApiOperation(value = "删除小节")
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){

        videoService.deleteVideo(id);

        return R.ok();
    }

}

