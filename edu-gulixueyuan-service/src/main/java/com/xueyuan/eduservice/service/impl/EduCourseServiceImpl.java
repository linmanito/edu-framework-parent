package com.xueyuan.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyuan.educommon.entity.ResultCode;
import com.xueyuan.educommon.exception.ServiceException;
import com.xueyuan.eduservice.client.EduClient;
import com.xueyuan.eduservice.entity.*;
import com.xueyuan.eduservice.entity.dto.CourseDto;

import com.xueyuan.eduservice.entity.front.CourseFront;
import com.xueyuan.eduservice.entity.info.PublishCourseInfo;
import com.xueyuan.eduservice.entity.query.CourseQuery;
import com.xueyuan.eduservice.mapper.EduCourseMapper;
import com.xueyuan.eduservice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-07-09
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    EduCourseDescriptionService courseDescriptionService;

    @Autowired
    EduTeacherService teacherService;

    @Autowired
    EduVideoService videoService;

    @Autowired
    EduChapterService chapterService;

    //调用vod服务
    @Autowired
    private EduClient client;

    //添加课程信息
    @Override
    public String saveCourseAndDesription(CourseDto courseDto) {

        //先new一个educourse对象作为下面查询添加的条件
        EduCourse eduCourse = new EduCourse();

        //将传过来的vo对象拷贝给educourse
        BeanUtils.copyProperties(courseDto,eduCourse);

        //调自定义方法查询数据库中是否存在该对象数据
        EduCourse eduCourse1 = this.selectCourse(eduCourse);



        System.out.println("课程信息：" + eduCourse1);

        if (eduCourse1 != null){
            //如果存在就返回false，不再进行下面的操作，
            throw new ServiceException(20001,"添加失败，已存在该数据");

            //对于基本信息一定要插入成功，插入不成功则不再进行任何操作，返回错误
        }

        int courseCount = baseMapper.insert(eduCourse);

        if (courseCount <= 0){
            throw new ServiceException(20001,"添加课程信息失败");
        }

        //将课程id作为返回值返回给网页，使课程id来进行传递，确定是同一个课程
        String courseId = eduCourse.getId();

        EduCourseDescription eduCourseDescription = new EduCourseDescription();

        //判定是否存在描述信息
        String description = courseDto.getDescription();

        //如果存在就添加描述信息至数据库，不存在也不影响
        if (!StringUtils.isEmpty(description)){

            eduCourseDescription.setDescription(description);

            eduCourseDescription.setId(courseId);

            boolean saveDescription = courseDescriptionService.save(eduCourseDescription);
        }



        return courseId;
    }


    //获取课程信息
    @Override
    public CourseDto getCourseById(String courseId) {

        EduCourse eduCourse = baseMapper.selectById(courseId);

        if (eduCourse == null){
            throw new ServiceException(20001,"该di不存在");
        }

        CourseDto courseDto = new CourseDto();

        BeanUtils.copyProperties(eduCourse,courseDto);



        //通过查询数据库中的课程描述来获取课程描述，然后通过这个课程描述来获取课程描述，并判断是否为空
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);

        if (courseDescription != null){
            String description = courseDescription.getDescription();
            //如果不为空
            if (!StringUtils.isEmpty(description)){
                courseDto.setDescription(description);//就将这个课程信息赋给dto
            }

        }



        return courseDto;
    }

    //修改课程信息
    @Override
    public void updateCourse(CourseDto courseDto) {

        //new一个course作为数据库修改
        EduCourse course = new EduCourse();

        //将传过来的dto复制给course
        BeanUtils.copyProperties(courseDto,course);

        //将数据库中course修改
        int count = baseMapper.updateById(course);

        if (count <= 0){
            throw new ServiceException(20001,"修改失败");

        }

        //通过传过来的dto来获取课程信息
        String description = courseDto.getDescription();

        //判断课程信息是否为空
//        if (!StringUtils.isEmpty(description)){
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(description);
        courseDescription.setId(courseDto.getId());
        courseDescriptionService.updateById(courseDescription);
//        }



    }


    //根据课程id获取课程所有信息
    @Override
    public PublishCourseInfo getPublishCourseInfo(String courseId) {

        PublishCourseInfo publishCourseInfo = baseMapper.getPublishCourseInfo(courseId);

        return publishCourseInfo;
    }


    /**
     * 分页进行条件查询
     * @param page1
     * @param courseQuery
     */
    @Override
    public void getCoursePageByQuery(Page<EduCourse> page1, CourseQuery courseQuery) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        if(courseQuery == null){//判断是否存在查询条件
            baseMapper.selectPage(page1,wrapper);//没有查询条件的时候进行分页
//            return;
        }else {

            String title = courseQuery.getTitle();
            String status = courseQuery.getStatus();

            //判断查询条件是否存在
            if (!StringUtils.isEmpty(title)) {
                wrapper.like("title", title);
            }

            if (!StringUtils.isEmpty(status)) {
                wrapper.eq("status", status);
            }

            wrapper.orderByAsc("price");//依据价格正序
            baseMapper.selectPage(page1, wrapper);//根据查询条件进行分页
        }



    }

    //删除课程
    @Override
    public void deleteCourseById(String courseId) {


        //获取该课程下的所有课时
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId);

        //查询出课时集合
        List<EduVideo> videoList = videoService.list(videoWrapper);


        //判断该课程下的课时集合是否为空
        if (videoList.size()>0){

            //遍历课时集合，放入视频id集合中
            List<String> videoIds = new ArrayList<>();


            for (int i = 0; i <videoList.size() ; i++) {

                if (!StringUtils.isEmpty(videoList.get(i).getVideoSourceId())){
                    videoIds.add(videoList.get(i).getVideoSourceId());
                }

            }

            client.deleteVideos(videoIds);

        }





        //删除课时
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        videoService.remove(videoQueryWrapper);

        //删除章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        chapterService.remove(chapterQueryWrapper);

        //删除描述
        courseDescriptionService.removeById(courseId);

        //删除课程
        baseMapper.deleteById(courseId);

    }

    @Override
    public List<EduCourse> getCourseByTeacherId(String teacherId) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = baseMapper.selectList(wrapper);

        return courseList;
    }

    @Override
    public Map<String, Object> getCoursePage(Page<EduCourse> page1) {

        baseMapper.selectPage(page1,null);
        Map<String,Object> map = new HashMap<>();

        long size = page1.getSize();//每页数据数
        long current = page1.getCurrent();//当前页
        long pages = page1.getPages();//总页数
        long total = page1.getTotal();//总记录数
        List<EduCourse> items = page1.getRecords();//当前页数据
        boolean hasNext = page1.hasNext();//是否有下一页
        boolean hasPrevious = page1.hasPrevious();//是否有上一页

        map.put("size",size);
        map.put("current",current);
        map.put("pages",pages);
        map.put("total",total);
        map.put("items",items);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);


        return map;
    }

    @Override
    public CourseFront getCourseSourse(String courseId) {


        this.updateCourseView(courseId);

        CourseFront courseFront = baseMapper.getCourseFront(courseId);



        return courseFront;
    }

    //更新浏览数
    @Override
    public void updateCourseView(String courseId) {
        EduCourse course = baseMapper.selectById(courseId);

        course.setViewCount(course.getViewCount() + 1);

        baseMapper.updateById(course);

    }


    //查询该课程信息是否存在
    public EduCourse selectCourse(EduCourse eduCourse){

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("title",eduCourse.getTitle());

        EduCourse eduCourse1 = baseMapper.selectOne(wrapper);

        return eduCourse1;

    }



}
