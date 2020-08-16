package com.atguigu.orderservice.client;

import com.atguigu.orderservice.client.impl.EduServiceClientImpl;
import com.atguigu.servicebase.dto.CourseWebDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author wu on 2020/8/16 0016
 */
@Component
@FeignClient(name = "service-edu", fallback =EduServiceClientImpl.class)
public interface EduServiceClient {

    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{courseId}")
    CourseWebDTO getCourseInfoOrder(@PathVariable("courseId") String courseId);
}
