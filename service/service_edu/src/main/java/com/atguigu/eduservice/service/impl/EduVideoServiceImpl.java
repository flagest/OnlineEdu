package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author zhengWu
 * @since 2020-07-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Resource
    private VodClient vodClient;

    @Override
    public R remVidAndAliyu(String id) {
        LambdaQueryWrapper<EduVideo> laEduVideo = new QueryWrapper<EduVideo>().lambda();
        laEduVideo.eq(EduVideo::getId, id).select(EduVideo::getVideoSourceId);
        EduVideo eduVideo = baseMapper.selectOne(laEduVideo);
        if (StringUtils.isEmpty(eduVideo))
            throw new GuLiException(20001, "该章节下没有改小节课程:(");
        // 删除阿里云视频
        String videoSourceId = eduVideo.getVideoSourceId();
        R r = vodClient.deleteVideo(videoSourceId);
        if (!r.isSuccess())
            throw new GuLiException(20001, "删除阿里云视频失败:(");
        int reslutDele = baseMapper.deleteById(id);
        if (reslutDele <= 0)
            throw new GuLiException(20001, "删除小节课程失败:(");
        return R.ok();
    }
}
