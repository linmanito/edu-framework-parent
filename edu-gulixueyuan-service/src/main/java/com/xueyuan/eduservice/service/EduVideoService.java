package com.xueyuan.eduservice.service;

import com.xueyuan.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyuan.eduservice.entity.dto.VideoDto;
import com.xueyuan.eduservice.entity.info.VideoInfoForm;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-07-11
 */
public interface EduVideoService extends IService<EduVideo> {

    //获取小节信息
    VideoInfoForm getVideoById(String id);

    //更改小节
    void updateVideo(VideoInfoForm videoInfoForm);

    //删除小节
    void deleteVideo(String id);

    //添加小节
    void saveVideo(VideoInfoForm videoInfoForm);
}
