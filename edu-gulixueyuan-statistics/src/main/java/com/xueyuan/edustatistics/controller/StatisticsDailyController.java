package com.xueyuan.edustatistics.controller;




import com.xueyuan.educommon.entity.R;
import com.xueyuan.edustatistics.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-07-16
 */
@RestController
@RequestMapping("/edustatistics/statistics-daily")
@Api(description = "统计数据管理")
@CrossOrigin
public class StatisticsDailyController {


    @Autowired
    private StatisticsDailyService statisticsService;

    @ApiOperation(value = "添加当日统计数据")
    @PostMapping("saveStatistcs/{day}")
    public R saveStatistcsByDay(@PathVariable String day){

        statisticsService.saveStatistcsByDay(day);

        return R.ok();
    }



    @ApiOperation(value = "根据查询条件显示图表")
    @GetMapping("showchart/{begin}/{end}/{type}")
    public R showChart(@PathVariable String begin,
                       @PathVariable String end,
                       @PathVariable String type){

        //返回一个键值对map结构，查询出来的都是集合
        Map<String,Object> map = statisticsService.showChartByTime(begin,end,type);

        return R.ok().data(map);
    }











}

