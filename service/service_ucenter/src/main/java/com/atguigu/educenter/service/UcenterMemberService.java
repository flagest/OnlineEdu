package com.atguigu.educenter.service;

import com.atguigu.commonutils.R;
import com.atguigu.educenter.VO.LoginVO;
import com.atguigu.educenter.VO.RegisterVO;
import com.atguigu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zhengWu
 * @since 2020-08-05
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    R register(RegisterVO registerVO);

    R findMemberInfo(HttpServletRequest request);

    R login(LoginVO loginVO);

    int countRegister(String day);
}
