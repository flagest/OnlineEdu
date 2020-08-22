package com.atguigu.edustatistics.scdedul;

import com.atguigu.edustatistics.service.StatisticsDailyService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * 定时任务配置类
 *
 * @author wu on 2020/8/22 0022
 */
@Configuration
public class SchedulTask {

    @Resource
    private StatisticsDailyService statisticsDailyService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void repetDoSomething() {
        statisticsDailyService.repatTask();
        System.out.println("这个表达式执行了定时任务++++++++++++++");
    }
}
