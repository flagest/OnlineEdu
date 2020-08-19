package com.atguigu.eduservice.client.impl;

import com.atguigu.eduservice.client.OrderClient;
import org.springframework.stereotype.Component;

/**
 * @author wu on 2020/8/19 0019
 */
@Component
public class OrderClientImpl implements OrderClient {
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {

        return false;
    }
}
