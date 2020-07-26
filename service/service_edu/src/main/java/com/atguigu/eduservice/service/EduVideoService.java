package com.atguigu.eduservice.service;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author zhengWu
 * @since 2020-07-18
 */
public interface EduVideoService extends IService<EduVideo> {

    R remVidAndAliyu(String id);
}
