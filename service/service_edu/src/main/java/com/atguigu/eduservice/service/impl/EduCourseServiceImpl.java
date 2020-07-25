package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.mapper.EduCourseDescriptionMapper;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.vo.CourseInfoVO;
import com.atguigu.eduservice.vo.CoursePublishVO;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zhengWu
 * @since 2020-07-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Resource
    private EduCourseDescriptionMapper eduCourseDescriptionMapper;

    @Resource
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Resource
    private EduCourseMapper eduCourseMapper;

    @Override
    public R addCourseInfo(CourseInfoVO courseInfoVO) {
        int insertCourse = 0;
        int insertCourseDesc = 0;
        EduCourse eduCourse;
        try {
            eduCourse = new EduCourse();
            BeanUtils.copyProperties(courseInfoVO, eduCourse);
            insertCourse = baseMapper.insert(eduCourse);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuLiException(20001, "保存课程信息出错了:(");
        }
        String courseId = eduCourse.getId();
        try {
            EduCourseDescription eduCourseDescription = new EduCourseDescription();
            BeanUtils.copyProperties(courseInfoVO, eduCourseDescription);
            eduCourseDescription.setId(courseId);
            insertCourseDesc = eduCourseDescriptionMapper.insert(eduCourseDescription);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuLiException(20002, "保存课程描述信息出错了:(");
        }

        return (insertCourse + insertCourseDesc) >= 2 ? R.ok().data("courseId", courseId) : R.error();
    }


    //根据课程id查询课程基本信息
    @Override
    public CourseInfoVO getCourseInfo(String courseId) {
        //1 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVO courseInfoVo = new CourseInfoVO();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        //2 查询描述表
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVO courseInfoVo) {
        //1 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0) {
            throw new GuLiException(20001, "修改课程信息失败");
        }

        //2 修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(description);
    }

    @Override
    public R getPublishCourseInfo(String courseId) {
        CoursePublishVO coursePublishVO = eduCourseMapper.getPublishCourseInfo(courseId);
        if (StringUtils.isEmpty(coursePublishVO))
            throw new GuLiException(20001, "没有该课程！");

        return R.ok().data("publishCourse", coursePublishVO);
    }
}
