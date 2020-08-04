package com.atguigu.msmservice.service;

import com.atguigu.commonutils.R;

/**
 * @author wu on 2020/8/4 0004
 */
public interface MsmService {
    R send(String phone);

    R veifing(String phone,String code);
}
