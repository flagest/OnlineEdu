package com.atguigu.orderservice.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.orderservice.client.EduServiceClient;
import com.atguigu.orderservice.client.UcenterOrderClient;
import com.atguigu.orderservice.entity.TOrder;
import com.atguigu.orderservice.mapper.TOrderMapper;
import com.atguigu.orderservice.service.TOrderService;
import com.atguigu.orderservice.utils.OrderNoUtil;
import com.atguigu.servicebase.dto.CourseWebDTO;
import com.atguigu.servicebase.dto.UcenterMemberDTO;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author zhengWu
 * @since 2020-08-16
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {
    @Resource
    private TOrderMapper tOrderMapper;
    @Resource
    private UcenterOrderClient ucenterOrderClient;
    @Resource
    private EduServiceClient eduServiceClient;

    @Override
    public R createOrder(String courseId, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId))
            throw new GuLiException(20001, "该用户没有登录:(");
        if (StringUtils.isEmpty(courseId))
            throw new GuLiException(20001, "该课程的id为空:(");
        UcenterMemberDTO userInfoOrder = ucenterOrderClient.getUserInfoOrder(memberId);
        CourseWebDTO courseInfoOrder = eduServiceClient.getCourseInfoOrder(courseId);
        TOrder tOrder = new TOrder();
        String orderId = OrderNoUtil.getOrderNo();//随机生成的订单号
        tOrder.setOrderNo(orderId);
        tOrder.setCourseId(courseId);
        tOrder.setCourseTitle(courseInfoOrder.getTitle());
        tOrder.setCourseCover(courseInfoOrder.getCover());
        tOrder.setTeacherName(courseInfoOrder.getTeacherName());
        tOrder.setMemberId(memberId);
        tOrder.setNickname(userInfoOrder.getNickname());
        tOrder.setMobile(userInfoOrder.getMobile());
        tOrder.setTotalFee(courseInfoOrder.getPrice());
        tOrder.setPayType(1);
        tOrder.setStatus(0);
        int effNum = tOrderMapper.insert(tOrder);
        return retBool(effNum) ? R.ok().data("orderId", orderId) : R.error();
    }
}
