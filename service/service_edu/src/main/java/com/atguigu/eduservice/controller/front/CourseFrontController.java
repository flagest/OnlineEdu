package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.vo.ChapterVO;
import com.atguigu.eduservice.vo.frontvo.CourseFrontVO;
import com.atguigu.eduservice.vo.frontvo.CourseWebVO;
import com.atguigu.servicebase.dto.CourseWebDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author wu on 2020/8/9 0009
 */
//@CrossOrigin
@RestController
@RequestMapping("/eduservice/coursefront")
@Api(description = "前台前端页面展示的课程信息接口")
public class CourseFrontController {

    @Resource
    private EduCourseService eduCourseService;
    @Resource
    private EduCourseMapper eduCourseMapper;
    @Resource
    private EduChapterService eduChapterService;

    @PostMapping("/getFrontCourseList/{page}/{limit}")
    @ApiOperation(value = "前台前端页面更具多种条件查询课程信息")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVO courseFrontVO) {
        return eduCourseService.getFrontCourseList(page, limit, courseFrontVO);

    }

    @GetMapping("getFrontCourseInfo/{courseId}")
    @ApiOperation(value = "更具课程id去查询课程详情方法")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request) {
        return eduCourseService.getCoursesInfo(courseId, request);
    }

    //根据课程id查询课程信息
    @PostMapping("/getCourseInfoOrder/{courseId}")
    @ApiOperation(value = "根据课程id查询课程信息")
    public CourseWebDTO getCourseInfoOrder(@PathVariable String courseId) {
        CourseWebVO courseWebVO = eduCourseMapper.getBaseCourseInfo(courseId);
        CourseWebDTO courseWebDTO = new CourseWebDTO();
        BeanUtils.copyProperties(courseWebVO, courseWebDTO);
        return courseWebDTO;
    }

    //根据id查询
    @GetMapping("/getById/{id}")
    @ApiOperation(value = "根据id去查询")
    public R getById(@PathVariable String id) {
        LambdaQueryWrapper<EduCourse> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(EduCourse::getId, id);
        return R.ok().data("ids", eduCourseService.getOne(lambdaQueryWrapper));
    }

    //新增或修改课程
    @PostMapping("/save")
    @ApiOperation(value = "新增或修改课程")
    public R saveOrUpdate(@RequestBody EduCourse eduCourse) {
        boolean b = eduCourseService.saveOrUpdate(eduCourse);
        return b ? R.ok() : R.error();

    }
}
