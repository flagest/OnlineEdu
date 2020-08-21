package com.atguigu.edustatistics.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.edustatistics.client.UcentrClient;
import com.atguigu.edustatistics.entity.StatisticsDaily;
import com.atguigu.edustatistics.mapper.StatisticsDailyMapper;
import com.atguigu.edustatistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author zhengWu
 * @since 2020-08-20
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Resource
    private UcentrClient statisticsClient;

    @Resource
    private StatisticsDailyMapper statisticsDailyMapper;

    @Override
    public R registerCount(String day) {
        int registerCount = statisticsClient.countRegister(day);
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(registerCount);
        statisticsDaily.setDateCalculated(day);
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100, 200));
        int effNum = statisticsDailyMapper.insert(statisticsDaily);
        return retBool(effNum) ? R.ok() : R.error();
    }
}
