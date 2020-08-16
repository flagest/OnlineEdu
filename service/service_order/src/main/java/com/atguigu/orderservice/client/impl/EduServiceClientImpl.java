package com.atguigu.orderservice.client.impl;

import com.atguigu.orderservice.client.EduServiceClient;
import com.atguigu.servicebase.dto.CourseWebDTO;
import org.springframework.stereotype.Component;

/**
 * @author wu on 2020/8/16 0016
 */
@Component
public class EduServiceClientImpl implements EduServiceClient {
    @Override
    public CourseWebDTO getCourseInfoOrder(String courseId) {
        return null;
    }
}
