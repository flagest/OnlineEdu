package com.atguigu.orderservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.orderservice.service.TPayLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author zhengWu
 * @since 2020-08-16
 */
//@CrossOrigin
@RestController
@RequestMapping("/orderservice/paylog")
@Api(description = "订单记录添相关接口")
public class TPayLogController {

    @Resource
    private TPayLogService logService;

    @GetMapping("/createPayLog/{orderNo}")
    @ApiOperation(value = "生成微信支付二维码接口")
    public R createPayLog(@PathVariable String orderNo) {
        return logService.createPayLog(orderNo);
    }

    //根据订单号查询支付状态
    @GetMapping("/queryPayStatus/{orderNo}")
    @ApiOperation(value = "查询订单支付状态")
    public R queryPayStatus(@PathVariable String orderNo) {
        return logService.queryPayStatus(orderNo);
    }
}

