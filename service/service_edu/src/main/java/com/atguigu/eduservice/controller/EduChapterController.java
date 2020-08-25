package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.vo.ChapterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zhengWu
 * @since 2020-07-18
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/chapter")
@Api(description = "课程章节接口", value = "课程章节接口")
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    //课程大纲列表,根据课程id进行查询
    @GetMapping("getChapterVideo/{courseId}")
    @ApiOperation(value = "课程章节查询")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVO> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo", list);
    }

    //添加课程章节
    @PostMapping("addChapter")
    @ApiOperation(value = "添加课程章节")
    public R addChapter(@RequestBody EduChapter chapter) {
        boolean result = chapterService.save(chapter);
        return result ? R.ok() : R.error();
    }

    //更具课程Id去查询
    @GetMapping("getChapterInfo/{chapterId}")
    @ApiOperation(value = "更具课程Id去查询")
    public R getCourseById(@PathVariable String chapterId) {
        EduChapter eduChapter = chapterService.getById(chapterId);
        return (eduChapter != null) ? R.ok().data("chapter", eduChapter) : R.error();
    }

    //修改课程
    @PostMapping("updateChapter")
    @ApiOperation(value = "修改课程")
    public R updateChapter(@RequestBody EduChapter chapter) {
        boolean result = chapterService.updateById(chapter);
        return result ? R.ok() : R.error();
    }

    //删除章节
    @DeleteMapping("{chapterId}")
    @ApiOperation(value = "删除章节")
    public R deleteChapter(@PathVariable String chapterId){
        return chapterService.deleteChapter(chapterId);

    }
}

