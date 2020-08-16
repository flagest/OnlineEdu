package com.atguigu.orderservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.orderservice.entity.TOrder;
import com.atguigu.orderservice.service.TOrderService;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author zhengWu
 * @since 2020-08-16
 */
@CrossOrigin
@RestController
@RequestMapping("/orderservice/order")
@Api(description = "用户添加订单信息接口")
public class TOrderController {

    @Resource
    private TOrderService tOrderService;

    @PostMapping("createOrder/{courseId}")
    @ApiOperation(value = "存入订单信息")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request) {
        return tOrderService.createOrder(courseId, request);
    }

    @GetMapping("/getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        LambdaQueryWrapper<TOrder> laQuerOrder = new LambdaQueryWrapper<>();
        laQuerOrder.eq(TOrder::getOrderNo, orderId);
        TOrder tOrder = tOrderService.getOne(laQuerOrder);
        if (StringUtils.isEmpty(tOrder))
            throw new GuLiException(20001, "该订单信息不存在:(");
        return R.ok().data("item", tOrder);
    }
}

