package com.xueyuan.eduvod.controller;


import com.xueyuan.educommon.entity.R;
import com.xueyuan.eduvod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@Api(description = "视频点播")
@RequestMapping("/eduvod/edu-video")
public class VideoAdminController {

    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "视频上传")
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file){

        String videoId = videoService.uploadVideo(file);

        return R.ok().data("videoId",videoId);

    }

    @ApiOperation(value = "删除云端视频")
    @DeleteMapping("deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable("videoId") String videoId){

        videoService.deleteVideo(videoId);

        return R.ok();
    }

    @ApiOperation(value = "批量删除云端视频")
    @DeleteMapping("deleteVideos")
    public R deleteVideos(@RequestParam("ids") List<String> ids){

        videoService.deleteVideoList(ids);


        return R.ok();
    }


    @ApiOperation(value = "获取播放凭证")
    @GetMapping("getPlayAuto/{videoId}")
    public R getPlayAuth(@PathVariable("videoId") String videoId){

        String playAuth = videoService.getPlayAuto(videoId);

        return R.ok().data("playAuth",playAuth);
    }





}
