package com.atguigu.eduservice.service;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.vo.CourseInfoVO;
import com.atguigu.eduservice.vo.CourseSerachVO;
import com.atguigu.eduservice.vo.frontvo.CourseFrontVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.validation.BindingResult;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zhengWu
 * @since 2020-07-18
 */
public interface EduCourseService extends IService<EduCourse> {

    R addCourseInfo(CourseInfoVO courseInfoVO);

    CourseInfoVO getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVO courseInfoVo);

    R getPublishCourseInfo(String courseId);

    R getCourseList(long current,long limit,CourseSerachVO courseSerachVO);


    R coursesAndOthers(String id);

    R getFrontCourseList(long page, long limit, CourseFrontVO courseFrontVO);


    R getCoursesInfo(String courseId);
}
