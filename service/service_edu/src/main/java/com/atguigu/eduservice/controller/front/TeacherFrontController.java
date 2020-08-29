package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wu on 2020/8/9 0009
 */
//@CrossOrigin
@RestController
@RequestMapping("/eduservice/techerfront")
@Api(description = "前台的前端页关于讲师相关接口")
public class TeacherFrontController {

    @Resource
    private EduTeacherService eduTeacherService;

    @PostMapping("/getTeacherFrontList/{page}/{limit}")
    @ApiOperation(value = "前台前端展示讲师页面所用的接口信息")
    public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit) {
        return eduTeacherService.selectByPage(page, limit);
    }

    @GetMapping("/getTeacherInfo/{teacherId}")
    @ApiOperation(value = "根据讲师id去查询讲师信息")
    public R getTeacherInfo(@PathVariable String teacherId) {
        return eduTeacherService.getTeacherInfo(teacherId);
    }
}
