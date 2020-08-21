package com.atguigu.edustatistics.service;

import com.atguigu.commonutils.R;
import com.atguigu.edustatistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author zhengWu
 * @since 2020-08-20
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    R registerCount(String day);

}
