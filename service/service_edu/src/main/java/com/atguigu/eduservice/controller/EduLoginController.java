package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 后台用户登录接口
 * @author wu on 2020/7/11 0011
 */
@RestController
@Api(description = "后台用户登录接口")
@RequestMapping("/eduservice/user")
//@CrossOrigin(origins = "http://localhost:9528")
public class EduLoginController {

    @PostMapping("login")
    @ApiOperation("登录接口返回token")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]")
                .data("name","[admin]")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
