package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-02-29
 */
//@CrossOrigin
@RestController
@RequestMapping("/eduservice/subject")
@Api(description = "课程接口", value = "课程接口")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //获取上传过来文件，把文件内容读取出来
    @PostMapping("addSubject")
    @ApiOperation(value = "课程列表Excel上传")
    public R addSubject(MultipartFile file) {
        //上传过来excel文件
        return subjectService.saveSubject(file, subjectService);

    }

    @GetMapping("getAllOneTwoSubject")
    @ApiOperation(value = "获取分类课程列表")
    public R getAllOneTwoSubject() {
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("data", list);
    }

}

