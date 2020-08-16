package com.atguigu.orderservice.service;

import com.atguigu.commonutils.R;
import com.atguigu.orderservice.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author zhengWu
 * @since 2020-08-16
 */
public interface TOrderService extends IService<TOrder> {

    R createOrder(String courseId, HttpServletRequest request);
}
