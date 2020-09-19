package com.atguigu.eduservice.client.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.UcenterClient;
import com.atguigu.servicebase.dto.UcenterMemberDTO;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wu on 2020/8/15 0015
 */
@Component
public class UcenterClientImpl implements UcenterClient {
    @Override
    public R getMemberInfo(HttpServletRequest request) {

        return R.error().message("根据前端传入token获取用户信息失败:(");
    }

    @Override
    public UcenterMemberDTO getUserInfoOrder(String memerId) {
        return null;
    }
}
