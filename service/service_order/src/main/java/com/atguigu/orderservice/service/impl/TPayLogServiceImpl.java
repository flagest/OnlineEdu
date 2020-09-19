package com.atguigu.orderservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.commonutils.R;
import com.atguigu.orderservice.entity.TOrder;
import com.atguigu.orderservice.entity.TPayLog;
import com.atguigu.orderservice.mapper.TPayLogMapper;
import com.atguigu.orderservice.service.TOrderService;
import com.atguigu.orderservice.service.TPayLogService;
import com.atguigu.orderservice.utils.HttpClient;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author zhengWu
 * @since 2020-08-16
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {

    @Resource
    private TPayLogMapper tPayLogMapper;

    @Resource
    private TOrderService tOrderService;


    @Override
    public R createPayLog(String orderNo) {
        try {
            //1 更据订单号查询订单信息
            LambdaQueryWrapper<TOrder> laQuerOrder = new LambdaQueryWrapper<>();
            laQuerOrder.eq(TOrder::getOrderNo, orderNo);
            TOrder tOrder = tOrderService.getOne(laQuerOrder);
            //2 使用Map设置生成二维码参数
            Map<String, String> mapParm = new HashMap<>();
            mapParm.put("appid", "");
            mapParm.put("mch_id", "");
            mapParm.put("nonce_str", WXPayUtil.generateNonceStr());
            mapParm.put("body", tOrder.getCourseTitle());
            mapParm.put("out_trade_no", orderNo);
            mapParm.put("total_fee", tOrder.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
            mapParm.put("spbill_create_ip", "127.0.0.1");
            mapParm.put("notify_url", "");
            mapParm.put("trade_type", "NATIVE");
            //3发送httpClient请求，传递参数为xml格式
            HttpClient client = new HttpClient("");

            //client设置参数
            client.setXmlParam(WXPayUtil.generateSignedXml(mapParm, ""));
            client.setHttps(true);
            client.post();
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //4得到发送请求结果
            Map map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", tOrder.getCourseId());
            map.put("total_fee", tOrder.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));//放回二维码操作状态
            map.put("code_url", resultMap.get("code_url"));//二维码地址
            return R.ok().data(map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuLiException(20001, "生成二维码失败:(");
        }

    }

    @Override
    public R queryPayStatus(String orderNo) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "");
            m.put("mch_id", "");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            HttpClient client = new HttpClient("");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, ""));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //6、转成Map
            //7、返回
            if (resultMap == null) {//出错
                return R.error().message("支付出错:(");
            }
            if (resultMap.get("trade_state").equals("SUCCESS")) {//如果成功
                //更改订单状态
                this.updateOrderStatus(resultMap);
                return R.ok().message("支付成功:)");
            }

            return R.ok().code(25000).message("支付中...");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateOrderStatus(Map<String, String> map) {
        //获取订单id
        String orderNo = map.get("out_trade_no");
        //根据订单id查询订单信息
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        TOrder order = tOrderService.getOne(wrapper);

        if (order.getStatus().intValue() == 1) return;
        order.setStatus(1);
        tOrderService.updateById(order);

        //记录支付日志
        TPayLog payLog = new TPayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(payLog);//插入到支付日志表
    }
}
