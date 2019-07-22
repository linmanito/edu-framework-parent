package com.xueyuan.eduservice.entity.info;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VideoInfoForm {

    @ApiModelProperty(value = "视频ID")
    private String id;

    @ApiModelProperty(value = "课程ID")
    private String courseId;

    @ApiModelProperty(value = "章节ID")
    private String chapterId;

    @ApiModelProperty(value = "节点名称")
    private String title;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "是否可以试听：1免费 0收费")
    private Boolean isFree;

    @ApiModelProperty(value = "视频资源")
    private String videoSourceId;

    //视频名称
    private String videoOriginalName;

}
