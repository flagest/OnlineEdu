package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wu on 2020/8/4 000
 */
@CrossOrigin
@RestController
@RequestMapping("/edumsm/msm")
@Api(description = "发送短信接口")
public class MsmController {
    @Resource
    private MsmService msmService;

    @GetMapping("/sent/{phone}")
    @ApiOperation(value = "接受到电话号码并发送短信")
    public R send(@PathVariable String phone) {
        return msmService.send(phone);
    }

    @GetMapping("/veifing/{phone}/{code}")
    @ApiOperation(value = "接受到电话号码和验证码并做检验")
    public R veifingCode(@PathVariable String phone, @PathVariable String code) {
        return msmService.veifing(phone, code);

    }
}
