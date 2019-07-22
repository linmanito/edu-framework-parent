package com.xueyuan.edustatistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xueyuan.edustatistics.client.UcenterClient;
import com.xueyuan.edustatistics.entity.StatisticsDaily;
import com.xueyuan.edustatistics.mapper.StatisticsDailyMapper;
import com.xueyuan.edustatistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-07-16
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {


    @Autowired
    UcenterClient ucenterClient;

    @Override
    public void saveStatistcsByDay(String day) {

        //获取当日的注册人数
        Integer count = (Integer) ucenterClient.getRegisterCount(day).getData().get("count");

        //在添加的时候先要清除当天数据库中的数据，再进行添加
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(count);
        statisticsDaily.setDateCalculated(day);

        //模拟数据
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100,200));

        //加入数据库
        baseMapper.insert(statisticsDaily);





    }

    @Override
    public Map<String, Object> showChartByTime(String begin, String end, String type) {

        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select(type,"date_calculated");

        List<StatisticsDaily> list = baseMapper.selectList(wrapper);

        Map<String,Object> dailyMap = new HashMap<>();
        List<String> dateList = new ArrayList<>();
        List<Integer> dataList = new ArrayList<>();
        dailyMap.put("date",dateList);
        dailyMap.put("data",dataList);

        for (int i = 0; i <list.size() ; i++) {

            StatisticsDaily daily = list.get(i);

            dateList.add(daily.getDateCalculated());

            switch (type){
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }


        return dailyMap;
    }
}
