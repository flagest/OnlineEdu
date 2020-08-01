package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.eduservice.vo.CoursesAndTeachersVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wu on 2020/8/1 0001
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/indexfront")
@Api(description = "返回前8个名师，前4个名师")
public class IndexFrontController {

    @Resource
    private EduTeacherService eduTeacherService;

    @Resource
    private EduCourseService eduCourseService;


    @GetMapping("index")
    @Cacheable(key = "'coursesAndTeachers'", value = "coursesAndTeachersVO")
    @ApiOperation(value = "返回前8个讲师和返回前4个课程")
    public R getTeachersAndCourses() {
        //返回前8个讲师
        LambdaQueryWrapper<EduCourse> laWraEduCourse = new LambdaQueryWrapper<>();
        laWraEduCourse.orderByDesc(EduCourse::getId).last("limit 8");
        //返回前4个课程
        LambdaQueryWrapper<EduTeacher> laWraEduTeacher = new LambdaQueryWrapper<>();
        laWraEduTeacher.orderByDesc(EduTeacher::getId).last("limit 4");
        CoursesAndTeachersVO coursesAndTeachersVO = new CoursesAndTeachersVO();
        coursesAndTeachersVO.setCourseList(eduCourseService.list(laWraEduCourse));
        coursesAndTeachersVO.setTeacherList(eduTeacherService.list(laWraEduTeacher));
        return R.ok().data("coursesAndTeachersVO", coursesAndTeachersVO);
    }
}
