package com.xueyuan.eduservice.entity.dto;

import lombok.Data;

@Data
public class VideoDto {

    //小节id
    private String id;

    private String title;

    private String videoSourceId;//视频Id

    private Integer sort;//排序

}
