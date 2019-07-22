package com.xueyuan.eduservice.client;


import com.xueyuan.educommon.entity.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("guli-vod")//指定被调用端的服务名
@Component
public interface EduClient {

    @DeleteMapping(value = "/eduvod/edu-video/deleteVideo/{videoId}")//指定方法
    public R deleteVideo(@PathVariable("videoId") String videoId);

    @DeleteMapping("/eduvod/edu-video/deleteVideos")
    public R deleteVideos(@RequestParam("ids") List<String> ids);



}
