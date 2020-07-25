package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.emnu.ResultEnum;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.eduservice.vo.TeacherQuery;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-04
 */
@Slf4j
@RestController
@RequestMapping("/eduservice/teacher")
@Api(description = "教育模块里面老师相关信息接口")
@CrossOrigin(origins = "http://localhost:9528")
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("/findAll")
    @ApiOperation(value = "查询所有教师信息接口")
    public R findAll() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items", list);
    }

    /*
     * 测试逻辑删除

     * */
    @DeleteMapping("{id}")
    @ApiOperation(value = "逻辑上删除教师信息")
    public R delRes(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
        boolean result = eduTeacherService.removeById(id);
        return result ? R.ok() : R.error();
    }

    @GetMapping("/pageTeacher/{current}/{limit}")
    @ApiOperation(value = "查询所有并分页")
    public R pageListTeacher(@PathVariable long current, @PathVariable long limit) {
        return eduTeacherService.pageListTeacher(current, limit);
    }

    @PostMapping("pageTeacherCondition/{current}/{limit}")
    @ApiOperation(value = "多条件查询并分页")
    public R pageCondition(@PathVariable long current, @PathVariable long limit,
                           @RequestBody(required = false) TeacherQuery teacherQuery) {
        return eduTeacherService.pageCondition(current, limit, teacherQuery);

    }


    @ApiOperation(value = "新增讲师")
    @PostMapping("addTeacher")
    public R save(@ApiParam(name = "teacher", value = "讲师对象", required = true) @RequestBody EduTeacher teacher) {
        return eduTeacherService.saveTeacherMessage(teacher);
    }


    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("{id}")
    public R getById(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("item", teacher);
    }

    @ApiOperation(value = "更具id讲师修改功能")
    @PostMapping("updateTeacher")
    public R modeifyTeacher(@RequestBody(required = true) EduTeacher teacher) {
        return eduTeacherService.updateById(teacher) ? R.ok() : R.error();
    }
}
