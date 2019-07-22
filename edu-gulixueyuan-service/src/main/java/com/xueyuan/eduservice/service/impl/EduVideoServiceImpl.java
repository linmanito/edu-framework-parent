package com.xueyuan.eduservice.service.impl;

import com.xueyuan.educommon.entity.R;
import com.xueyuan.educommon.exception.ServiceException;
import com.xueyuan.eduservice.client.EduClient;
import com.xueyuan.eduservice.entity.EduVideo;
import com.xueyuan.eduservice.entity.dto.VideoDto;
import com.xueyuan.eduservice.entity.info.VideoInfoForm;
import com.xueyuan.eduservice.mapper.EduVideoMapper;
import com.xueyuan.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-07-11
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {


    @Autowired
    private EduClient client;

    //获取小节信息
    @Override
    public VideoInfoForm getVideoById(String id) {

        EduVideo video = baseMapper.selectById(id);

        if (video == null){
            throw new ServiceException(20001,"该小节不存在");
        }

        VideoInfoForm videoInfoForm = new VideoInfoForm();

        BeanUtils.copyProperties(video,videoInfoForm);

        return videoInfoForm;
    }

    //修改小节信息
    @Override
    public void updateVideo(VideoInfoForm videoInfoForm) {

        EduVideo video = new EduVideo();

        BeanUtils.copyProperties(videoInfoForm,video);

        int i = baseMapper.updateById(video);

        if (i <= 0){
            throw new ServiceException(20001,"修改失败");
        }





    }


    //删除小节信息
    @Override
    public void deleteVideo(String id) {

        EduVideo video = baseMapper.selectById(id);

        String videoSourceId = video.getVideoSourceId();

        if (!StringUtils.isEmpty(videoSourceId)){
            client.deleteVideo(videoSourceId);
        }

        int i = baseMapper.deleteById(id);



        if (i <= 0){
            throw new ServiceException(20001,"删除失败");
        }


    }


    //添加小节
    @Override
    public void saveVideo(VideoInfoForm videoInfoForm) {

        EduVideo video = new EduVideo();
        BeanUtils.copyProperties(videoInfoForm,video);

        int insert = baseMapper.insert(video);

        if (insert <= 0){
            throw new ServiceException(20001,"添加失败");
        }

    }
}
