package com.atguigu.edustatistics.controller;


import com.atguigu.commonutils.R;
import com.atguigu.edustatistics.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author zhengWu
 * @since 2020-08-20
 */
@CrossOrigin
@RestController
@RequestMapping("/edustatistics/sta")
@Api(description = "统计注册人数接口")
public class StatisticsDailyController {
    @Resource
    private StatisticsDailyService statisticsDailyService;

    @GetMapping("/registerCount/{day}")
    @ApiOperation(value = "在页面展示注册人数，并储存在统计表中")
    public R registerCount(@PathVariable String day){
        return statisticsDailyService.registerCount(day);
    }

}

