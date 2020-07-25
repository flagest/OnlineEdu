package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.vo.CourseInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.service.ApiListing;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zhengWu
 * @since 2020-07-18
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/course")
@Api(description = "课程相关接口")
public class EduCourseController {
    @Resource
    private EduCourseService eduCourseService;

    @PostMapping("addCourseInfo")
    @ApiOperation(value = "添加课程信息和课程描述信息")
    public R addCourseInfo(@RequestBody CourseInfoVO courseInfoVO) {
        return eduCourseService.addCourseInfo(courseInfoVO);

    }

    //根据课程id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    @ApiOperation(value = "根据课程id查询课程基本信息")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVO courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    @ApiOperation(value = "修改课程信息")
    public R updateCourseInfo(@RequestBody CourseInfoVO courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    @GetMapping("getPublishCourseInfo/{courseId}")
    @ApiOperation(value = "最后展示更具id查询课程信息")
    public R getPublishCourseInfo(@PathVariable String courseId) {
        return eduCourseService.getPublishCourseInfo(courseId);
    }

    //课程最终发布，修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        return eduCourseService.updateById(eduCourse) ? R.ok() : R.error();
    }
}

