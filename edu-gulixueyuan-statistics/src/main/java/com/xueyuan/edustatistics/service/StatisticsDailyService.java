package com.xueyuan.edustatistics.service;

import com.xueyuan.edustatistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-07-16
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void saveStatistcsByDay(String day);

    Map<String,Object> showChartByTime(String begin, String end, String type);
}
