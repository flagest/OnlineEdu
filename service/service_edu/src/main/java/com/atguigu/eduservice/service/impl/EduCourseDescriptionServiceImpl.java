package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.mapper.EduCourseDescriptionMapper;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author zhengWu
 * @since 2020-07-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

}
