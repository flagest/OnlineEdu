package com.atguigu.educenter.client.impl;

import com.atguigu.commonutils.R;
import com.atguigu.educenter.client.MsmClinet;
import org.springframework.stereotype.Component;

/**
 * @author wu on 2020/8/6 0006
 */
@Component
public class MsmClinetImpl implements MsmClinet {
    @Override
    public R veifingCode(String phone, String code) {
        return R.error().message("在校验验证码时候出错了:(");
    }
}
