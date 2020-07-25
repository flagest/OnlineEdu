package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author wu on 2020/7/12 0012
 */
@RestController
@CrossOrigin
@RequestMapping("/eduoss/fileoss")
@Api(value = "上传图片", description = "上传图片")
public class OssController {
    @Resource
    private OssService service;

    @PostMapping("/upload")
    @ApiOperation(value = "上传图片")
    public R uploadOssFile(MultipartFile file) {
        String url = service.uploadFile(file);
        return R.ok().data("url", url);
    }
}
