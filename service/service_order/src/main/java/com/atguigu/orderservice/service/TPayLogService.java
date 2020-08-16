package com.atguigu.orderservice.service;

import com.atguigu.commonutils.R;
import com.atguigu.orderservice.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author zhengWu
 * @since 2020-08-16
 */
public interface TPayLogService extends IService<TPayLog> {

    R createPayLog(String orderNo);

    R queryPayStatus(String orderNo);
}
