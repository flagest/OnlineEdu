package com.atguigu.eduservice.vo;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import lombok.Data;

import java.util.List;

/**
 * @author wu on 2020/8/1 0001
 */
@Data
public class CoursesAndTeachersVO {
    List<EduCourse> courseList;
    List<EduTeacher> teacherList;
}
