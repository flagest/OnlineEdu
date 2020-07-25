package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zhengWu
 * @since 2020-07-18
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/video")
@Api(description = "章节播放")
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;


    @PostMapping("addVideo")
    @ApiOperation(value = "视频新增")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        boolean result = eduVideoService.save(eduVideo);
        return result ? R.ok() : R.error();
    }


    @DeleteMapping("{id}")
    @ApiOperation(value = "视频删除")
    //TODO 以后会将这个方法完善
    public R deleteVideo(@PathVariable String id) {
        boolean result = eduVideoService.removeById(id);
        return result ? R.ok() : R.error();
    }

}

