package com.xueyuan.eduservice.service;

import com.xueyuan.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyuan.eduservice.entity.dto.ChapterDto;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-07-11
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterDto> getAllChapterList(String courseId);

    void saveChapter(EduChapter chapter);

    boolean deleteChapter(String id);

    ChapterDto getChapterById(String id);

    boolean updateChapter(EduChapter chapter);
}
