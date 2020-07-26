package com.atguigu.vod.controller;

import com.atguigu.commonutils.R;
import com.atguigu.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin
@RestController
@RequestMapping("/eduvod/video")
@Api(value = "视频上传接口",description ="视频上传接口" )
public class VodController {

    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @PostMapping("uploadAlyiVideo")
    @ApiOperation(value = "视频上传到阿里与接口")
    public R uploadAlyiVideo(MultipartFile file) {
        //返回上传视频id
        return vodService.uploadAlyiVideo(file);
    }

    @DeleteMapping("/{videoSourceId}")
    @ApiOperation(value = "更具视频id删除阿里云视频")
    public R deleteVideo(@PathVariable String videoSourceId) {
        return vodService.deleteVideoById(videoSourceId);
    }
}
