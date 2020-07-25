package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.vo.CoursePublishVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zhengWu
 * @since 2020-07-18
 */
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    //更具id查询课程信息
    public CoursePublishVO getPublishCourseInfo(@Param("courseId") String courseId);


}
