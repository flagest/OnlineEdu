package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.vo.frontvo.CourseFrontVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wu on 2020/8/9 0009
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/coursefront")
@Api(description = "前台前端页面展示的课程信息接口")
public class CourseFrontController {

    @Resource
    private EduCourseService eduCourseService;

    @Resource
    private EduChapterService chapterService;

    @PostMapping("/getFrontCourseList/{page}/{limit}")
    @ApiOperation(value = "前台前端页面更具多种条件查询课程信息")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVO courseFrontVO) {
        return eduCourseService.getFrontCourseList(page, limit, courseFrontVO);

    }

    @GetMapping("getFrontCourseInfo/{courseId}")
    @ApiOperation(value = "更具课程id去查询课程详情方法")
    public R getFrontCourseInfo(@PathVariable String courseId) {
        eduCourseService.getBaseCourseInfo(courseId);
        chapterService.getChapterVideoByCourseId(courseId);
        return R.ok();

    }

}
