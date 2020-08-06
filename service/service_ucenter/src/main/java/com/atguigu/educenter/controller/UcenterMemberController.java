package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.educenter.VO.LoginVO;
import com.atguigu.educenter.VO.RegisterVO;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zhengWu
 * @since 2020-08-05
 */
@RestController
@CrossOrigin
@RequestMapping("/educenter/member")
@Api(description = "用户登录，注册接口")
public class UcenterMemberController {

    @Resource
    private UcenterMemberService ucenterMemberService;

   @PostMapping("login")
   @ApiOperation(value = "会员登录")
   public R login(@RequestBody LoginVO loginVO){
       return ucenterMemberService.login(loginVO);
   }


    @PostMapping("register")
    @ApiOperation(value = "用户注册")
    public R register(@RequestBody RegisterVO registerVO) {
        return ucenterMemberService.register(registerVO);
    }

    @GetMapping("/getMemberInfo")
    @ApiOperation(value = "更具token返回给前端相应的用户信息")
    public R getMemberInfo(HttpServletRequest request){
        return ucenterMemberService.findMemberInfo(request);
    }

}

