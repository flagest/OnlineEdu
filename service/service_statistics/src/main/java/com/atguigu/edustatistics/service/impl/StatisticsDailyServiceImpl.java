package com.atguigu.edustatistics.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.commonutils.R;
import com.atguigu.edustatistics.client.UcentrClient;
import com.atguigu.edustatistics.entity.StatisticsDaily;
import com.atguigu.edustatistics.mapper.StatisticsDailyMapper;
import com.atguigu.edustatistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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

    private String repatDay = "";

    @Override
    public R registerCount(String day) {
        repatDay = day;
        LambdaQueryWrapper<StatisticsDaily> laQueryStatics = new QueryWrapper<StatisticsDaily>().lambda();
        laQueryStatics.eq(StatisticsDaily::getDateCalculated, day);
        statisticsDailyMapper.delete(laQueryStatics);


        int registerCount = statisticsClient.countRegister(day);
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(registerCount);
        statisticsDaily.setDateCalculated(day);
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100, 200));
   /*     int effNum = statisticsDailyMapper.insert(statisticsDaily);
        return retBool(effNum) ? R.ok() : R.error();*/
        return R.ok();
    }


    /**
     * @param * @param null
     * @Date:15:57 2020/8/22 0022
     * @Description:每天凌晨一点钟做前一天的定时任务
     * @retrun
     */
    public boolean repatTask() {
        LambdaQueryWrapper<StatisticsDaily> laQueryStatics = new QueryWrapper<StatisticsDaily>().lambda();
        laQueryStatics.eq(StatisticsDaily::getDateCalculated, repatDay);
        statisticsDailyMapper.delete(laQueryStatics);


        int registerCount = statisticsClient.countRegister(repatDay);
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(registerCount);
        statisticsDaily.setDateCalculated(repatDay);
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100, 200));
        int effNum = statisticsDailyMapper.insert(statisticsDaily);
        return retBool(effNum);
    }

    @Override
    public R showData(String type, String begin, String end) {
      /*  LambdaQueryWrapper<StatisticsDaily> lqQueryStatistic = new LambdaQueryWrapper<>();
        lqQueryStatistic.between(StatisticsDaily::getDateCalculated, begin, end)
                .select(StatisticsDaily::getDateCalculated, StatisticsDaily::getRegisterNum
                        , StatisticsDaily::getLoginNum, StatisticsDaily::getCourseNum
                        , StatisticsDaily::getVideoViewNum)
                .orderByAsc(StatisticsDaily::getDateCalculated);*/
        QueryWrapper<StatisticsDaily> lqQueryStatistic = new QueryWrapper<>();
        lqQueryStatistic.between("date_calculated", begin, end)
                .select("date_calculated", type)
                .orderByAsc("date_calculated");

        List<StatisticsDaily> staList = statisticsDailyMapper.selectList(lqQueryStatistic);
        //日期的总数
        List<String> dateCalculatedList = new ArrayList<>();
        //要查询记录数
        List<Integer> numDataList = new ArrayList<>();
        staList.stream().forEach(statis -> {
            dateCalculatedList.add(statis.getDateCalculated());
            switch (type) {
                case "login_num":
                    numDataList.add(statis.getLoginNum());
                    break;
                case "register_num":
                    numDataList.add(statis.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(statis.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(statis.getCourseNum());
                    break;
                default:
                    break;
            }

        });

        Map<String, Object> data = new HashMap<>();
        data.put("date_calculatedList", dateCalculatedList);
        data.put("numDataList", numDataList);


        return R.ok().data(data);
    }

}
