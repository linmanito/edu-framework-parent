package com.xueyuan.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyuan.eduservice.entity.EduTeacher;
import com.xueyuan.eduservice.entity.query.TeacherQuery;
import com.xueyuan.eduservice.mapper.EduTeacherMapper;
import com.xueyuan.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-07-06
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {


    public void pageQuery(Page<EduTeacher> page, TeacherQuery teacherQuery) {

        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();//查询条件

        if (teacherQuery == null){
            baseMapper.selectPage(page ,queryWrapper);//baseMapper是service中自带，用这个来进行查询，并把
            // 查询到的数据放入Page分页类中
            //如果返回了，那么下面的就不再执行了
            return;
        }

        String name = teacherQuery.getName();
        String level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        System.out.println("姓名......."+name);

        if(!StringUtils.isEmpty(name)){//如果存在进行模糊查询
            queryWrapper.like("name" ,name);
        }

        if (!StringUtils.isEmpty(level)){//如果存在进行相等查询
            queryWrapper.eq("level" ,level);

        }

        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create" ,begin);
        }

        if (!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create" ,end);
        }

        queryWrapper.orderByDesc("gmt_create"); //根据时间进行倒序排序
        baseMapper.selectPage(page ,queryWrapper);

    }

    @Override
    public Map<String, Object> getListTeacherFront(Page<EduTeacher> pageTeacher) {

        //自己将分页数据封装到pageTeacher对象中
        baseMapper.selectPage(pageTeacher,null);
        Map<String,Object> map = new HashMap<>();
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        long pages = pageTeacher.getPages();//总页数
        long current = pageTeacher.getCurrent();//当前页
        long size = pageTeacher.getSize();//每页记录数
        boolean hasPrevious = pageTeacher.hasPrevious();//是否有上一页
        boolean hasNext = pageTeacher.hasNext();//是否有下一页

        map.put("total",total);
        map.put("records",records);
        map.put("pages",pages);
        map.put("current",current);
        map.put("size",size);
        map.put("hasPrevious",hasPrevious);
        map.put("hasNext",hasNext);

        return map;


    }
}
