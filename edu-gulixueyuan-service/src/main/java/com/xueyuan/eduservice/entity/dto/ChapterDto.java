package com.xueyuan.eduservice.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterDto {

    private String id;//章节id

    private String title;//章节名称

    private Integer sort;//排序

    private List<VideoDto> child = new ArrayList<>();//课时集合

}
