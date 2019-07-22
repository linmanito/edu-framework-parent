package com.xueyuan.eduservice.service;

import com.xueyuan.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyuan.eduservice.entity.dto.OneSubjectDto;
import com.xueyuan.eduservice.entity.dto.TwoSubjectDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-07-08
 */
public interface EduSubjectService extends IService<EduSubject> {

    //使用map缓存获取一级分类
    List<OneSubjectDto> subjectTreeListByMap();

    List<String> saveSubjectByPoi(MultipartFile file);

    List<OneSubjectDto> subjectTreeList();

    boolean deleteSubjectById(String id);

    boolean saveOneSubeject(EduSubject subject);

    boolean saveTwoSubject(EduSubject subject);
}
