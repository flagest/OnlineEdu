package com.atguigu.eduservice.client;

import com.atguigu.eduservice.client.impl.OrderClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author wu on 2020/8/19 0019
 */
@Component
@FeignClient(name = "service-order", fallback = OrderClientImpl.class)
public interface OrderClient {

    @GetMapping("/orderservice/order/isBuyCourse/{courseId}/{memberId}")
    boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
