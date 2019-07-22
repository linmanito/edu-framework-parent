package com.xueyuan.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xueyuan.educommon.exception.ServiceException;
import com.xueyuan.eduservice.client.EduClient;
import com.xueyuan.eduservice.entity.EduChapter;
import com.xueyuan.eduservice.entity.EduVideo;
import com.xueyuan.eduservice.entity.dto.ChapterDto;
import com.xueyuan.eduservice.entity.dto.VideoDto;
import com.xueyuan.eduservice.mapper.EduChapterMapper;
import com.xueyuan.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyuan.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-07-11
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;



    //获取所有章节信息
    @Override
    public List<ChapterDto> getAllChapterList(String courseId) {

        //根据课程ID查询所有章节
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.orderByAsc("sort");

        List<EduChapter> chapterList = baseMapper.selectList(wrapper);

        //根据课程ID查询所有小节
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId);
        videoWrapper.orderByAsc("sort");

        List<EduVideo> videoList = eduVideoService.list(videoWrapper);

        //返回的最终结果
        List<ChapterDto> chapterDtoList = new ArrayList<>();
        for (int i = 0; i <chapterList.size() ; i++) {

            //遍历出eduChapter放入结果集中
            EduChapter eduChapter = chapterList.get(i);
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(eduChapter,chapterDto);
            chapterDtoList.add(chapterDto);

            //创建放入章节中的小节集合
            List<VideoDto> videoDtoList = new ArrayList<>();

            for (int j = 0; j <videoList.size() ; j++) {

                EduVideo eduVideo = videoList.get(j);

                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoDto videoDto = new VideoDto();
                    BeanUtils.copyProperties(eduVideo,videoDto);

                    videoDtoList.add(videoDto);

                }




            }

            //将小节集合放入章节中
            chapterDto.setChild(videoDtoList);

        }
        //返回
        return chapterDtoList;
    }

    @Override
    public void saveChapter(EduChapter eduChapter) {



        int insert = baseMapper.insert(eduChapter);

        if (insert <= 0) {

            throw new ServiceException(20001, "添加失败");

        }

    }


    //删除章节
    @Override
    public boolean deleteChapter(String id) {

        //通过章节id查询是否有小节,有小节就不能删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        //只需要查询条数就行，条数大于0就代表该章节下有小节
        int count = eduVideoService.count(wrapper);
        if (count > 0){

            throw new ServiceException(20001,"该章节有子节点无法删除");

        }
        int i = baseMapper.deleteById(id);
        if (i<=0){
            throw new ServiceException(20001,"删除失败");
        }

        return true;

    }

    @Override
    public ChapterDto getChapterById(String id) {


        EduChapter chapter = baseMapper.selectById(id);


        if (chapter == null){
            throw new ServiceException(20001,"获取数据失败");
        }

        ChapterDto chapterDto = new ChapterDto();
        BeanUtils.copyProperties(chapter,chapterDto);

        return chapterDto;
    }

    @Override
    public boolean updateChapter(EduChapter chapter) {


        int i = baseMapper.updateById(chapter);

        if (i <= 0){
            throw new ServiceException(20001,"修改失败");
        }

        return true;
    }
}
