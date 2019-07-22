package com.xueyuan.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xueyuan.eduservice.entity.EduSubject;
import com.xueyuan.eduservice.entity.dto.OneSubjectDto;
import com.xueyuan.eduservice.entity.dto.TwoSubjectDto;
import com.xueyuan.eduservice.mapper.EduSubjectMapper;
import com.xueyuan.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.BaseStream;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-07-08
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public List<String> saveSubjectByPoi(MultipartFile file) {

        try {

            //1.通过MultipartFile获取输入流
            InputStream in = file.getInputStream();
            //2.获取工作簿
            Workbook workbook = WorkbookFactory.create(in);
            //3.获取表
            Sheet sheet = workbook.getSheetAt(0);

            //3.获取行
            int lastRowNum = sheet.getLastRowNum();//获取该表中最后一行的索引值

            List<String> res = new ArrayList<>();

            for (int i = 1; i <= lastRowNum ; i++) {//因为第一行不用读取，所以从第二行也就是i=1开始

                Row row = sheet.getRow(i);

                //获取第一列
                Cell oneCell = row.getCell(0);

                String result = "";//有空的返回结果

                if (oneCell == null){
                    result = "第"+(i+1)+"行，第1列值为空";
                    res.add(result);
                    continue;//则跳过这次循环
                }

                int cellType = oneCell.getCellType();   //获取第一列的类型
                String oneCellValue = "";
                switch (cellType){

                    case HSSFCell.CELL_TYPE_STRING://String型
                        oneCellValue = oneCell.getStringCellValue();
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC://int型
                        oneCellValue = String.valueOf(oneCell.getNumericCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_BOOLEAN://布尔型
                        oneCellValue = String.valueOf(oneCell.getBooleanCellValue());
                        break;

                }

                if (StringUtils.isEmpty(oneCellValue)){

                    result = "第"+(i+1)+"行，第1列值为空";
                    res.add(result);
                    continue;

                }

                EduSubject oneSubject = this.getByTitle(oneCellValue);//查询数据库中是否有该一级分类

                String pid = null;//设置一级分类id

                if(oneSubject == null){

                    EduSubject oneEduSubject =  new EduSubject();

                    oneEduSubject.setTitle(oneCellValue);

                    oneEduSubject.setParentId("0");

                    baseMapper.insert(oneEduSubject);

                    pid = oneEduSubject.getId();

                }else{
                    pid = oneSubject.getId();
                }
                
                //获取第二列信息
                Cell twoCell = row.getCell(1);

                if (twoCell == null){
                    result = "第"+(i+1)+"行，第2列值为空";
                    res.add(result);
                    continue;//则跳过这次循环
                }

                //获取第二列的类型
                int twoCellType = twoCell.getCellType();
                //获取第二列的值
                String twoCellValue = "";

                switch (twoCellType){
                    case HSSFCell.CELL_TYPE_STRING:
                        twoCellValue = twoCell.getStringCellValue();
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        twoCellValue = String.valueOf(twoCell.getNumericCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        twoCellValue = String.valueOf(twoCell.getBooleanCellValue());
                        break;
                }

                if (StringUtils.isEmpty(twoCellValue)){

                    result = "第"+(i+1)+"行，第2列值为空";
                    res.add(result);
                    continue;

                }

                //查询第二列中的值在数据库中是否存在
                EduSubject twoSubject = this.getSubByTitle(twoCellValue, pid);

                if (twoSubject == null) {

                    EduSubject twoEduSubject = new EduSubject();
                    twoEduSubject.setTitle(twoCellValue);
                    twoEduSubject.setParentId(pid);

                    baseMapper.insert(twoEduSubject);

                }


            }

            return  res;

            //4.获取列

            //5.写入数据库中

        }catch (Exception e){
            e.printStackTrace();
        }


        return null;


    }

    @Override
    public List<OneSubjectDto> subjectTreeList() {

        //获取所有的父节点
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id","0");
        List<EduSubject> oneSubjects = baseMapper.selectList(wrapper);

        //获取所有子节点
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
        wrapper1.ne("parent_id",0);
        List<EduSubject> twoSubjects = baseMapper.selectList(wrapper1);

        //创建dto父节点集合
        List<OneSubjectDto> oneSubjectDtos = new ArrayList<>();

        //将获取到的父节点遍历
        for (int i = 0; i <oneSubjects.size() ; i++) {


            OneSubjectDto oneSubjectDto = new OneSubjectDto();

            //将edusubject中的父节点拷贝给dto父节点
            BeanUtils.copyProperties(oneSubjects.get(i) , oneSubjectDto);

            //创建dto子节点集合
            List<TwoSubjectDto> twoSubjectDtos = new ArrayList<>();

            //遍历EduSubject子节点
            for (int j = 0; j < twoSubjects.size(); j++) {



                //判断属于该父节点的子节点
                if (twoSubjects.get(j).getParentId().equals(oneSubjects.get(i).getId())){

                    TwoSubjectDto twoSubjectDto = new TwoSubjectDto();

                    BeanUtils.copyProperties(twoSubjects.get(j),twoSubjectDto);

                    twoSubjectDtos.add(twoSubjectDto);

                }

            }

            //将子节点集合放入父节点属性中
            oneSubjectDto.setChildren(twoSubjectDtos);



            oneSubjectDtos.add(oneSubjectDto);



        }


        return oneSubjectDtos;
    }



    //使用map缓存获取一级分类
    public List<OneSubjectDto> subjectTreeListByMap() {

        //获取所有的父节点
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id","0");
        List<EduSubject> oneSubjects = baseMapper.selectList(wrapper);

        //获取所有子节点
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
        wrapper1.ne("parent_id","0");
        List<EduSubject> twoSubjects = baseMapper.selectList(wrapper1);

        //创建dto父节点集合
        List<OneSubjectDto> oneSubjectDtos = new ArrayList<>();

        //创建map作为一级分类缓存区,key为一级分类id，value为封装的一级分类dto，里面封装了该一级分类
        // 下的所有二级分类集合
        Map<String , OneSubjectDto> oneSubjectMap = new HashMap<>();

        //将获取到的父节点遍历
        for (int i = 0; i <oneSubjects.size() ; i++) {

            //创建一个封装的一级分类dto
            OneSubjectDto oneSubjectDto = new OneSubjectDto();

            //然后进行数据对拷
            BeanUtils.copyProperties(oneSubjects.get(i),oneSubjectDto);

            //将该封装对象放入map集合中
            oneSubjectMap.put(oneSubjectDto.getId(),oneSubjectDto);

            //再将该封装对象放入要返回的结果集合中
            oneSubjectDtos.add(oneSubjectDto);
            //上面两个集合放入的都是同样的对象，而集合中放入的对象都是对象的引用地址，也就是，放入map和list
            //中的对象都是一个对象，修改其中一个集合中的对象属性，那么另一个集合中的该对象属性也会改变

        }



        for (int i = 0; i < twoSubjects.size(); i++) {
            //获取到二级分类对象
            EduSubject twoSubject = twoSubjects.get(i);

            //创建一个二级分类的封装对象
            TwoSubjectDto twoSubjectDto = new TwoSubjectDto();

            //进行对拷
            BeanUtils.copyProperties(twoSubject,twoSubjectDto);

            //通过二级分类获取到父Id
            String parentId = twoSubject.getParentId();

            //然后通过父Id从一级分类map中取出一级分类对象
            OneSubjectDto oneSubjectDto = oneSubjectMap.get(parentId);

            //判断该一级分类对象是否为空
            if (oneSubjectDto!= null){
                //不为空则将一级分类对象中的二级分类集合属性取出
                List<TwoSubjectDto> children = oneSubjectDto.getChildren();

                //然后将该封装的二级分类放入一级分类对象的二级分类集合中
                children.add(twoSubjectDto);
            }

        }


        //然后上面二级分类遍历完，map中的对象也就全部都有了二级分类的集合了，并且一级分类的list集合中的一级
        //分类对象也就有了二级分类的集合
        return oneSubjectDtos;
    }


    @Override
    public boolean deleteSubjectById(String id) {

        //老师方法
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        //查询父类id，然后返回查询条数
        Integer count = baseMapper.selectCount(wrapper);

        if (count > 0){
            return false;
        }else {
            int i = baseMapper.deleteById(id);
            if (i > 0){
                return true;
            }else {
                return false;
            }

        }

        /*EduSubject eduSubject = baseMapper.selectById(id);
        String parentId = eduSubject.getParentId();

        if (parentId.equals("0")){
            return false;
        }else {
            int i = baseMapper.deleteById(id);

            if (i > 0){
                return true;
            }else {
                return false;
            }
        }*/





    }

    //添加一级分类
    @Override
    public boolean saveOneSubeject(EduSubject subject) {

        String title = subject.getTitle();
        EduSubject oneSubject = this.getByTitle(title);


        if (oneSubject == null){



            subject.setParentId("0");

            int insert = baseMapper.insert(subject);
            return insert > 0;
        }

        return false;
    }

    //添加二级分类
    @Override
    public boolean saveTwoSubject(EduSubject subject) {

        String title = subject.getTitle();
        String parentId = subject.getParentId();

        EduSubject subByTitle = this.getSubByTitle(title, parentId);

        if (subByTitle == null){
            int insert = baseMapper.insert(subject);

            return insert > 0;
        }

        return false;

    }


    //查询一级分类
    private EduSubject getByTitle(String name){

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");

        EduSubject eduSubject = baseMapper.selectOne(wrapper);

        return eduSubject;

    }

    //查询二级分类
    private EduSubject getSubByTitle(String name,String pid){

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);

        EduSubject eduSubject = baseMapper.selectOne(wrapper);

        return eduSubject;

    }

}
