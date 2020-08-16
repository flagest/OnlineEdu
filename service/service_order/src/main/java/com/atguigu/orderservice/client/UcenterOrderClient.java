package com.atguigu.orderservice.client;


import com.atguigu.orderservice.client.impl.UcenterOrderClientImpl;
import com.atguigu.servicebase.dto.UcenterMemberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author wu on 2020/8/16 0016
 */
@Component
@FeignClient(name = "service-ucenter", fallback = UcenterOrderClientImpl.class)
public interface UcenterOrderClient {

    @PostMapping("/api/ucenter/member/getUserInfoOrder/{memerId}")
    UcenterMemberDTO getUserInfoOrder(@PathVariable("memerId") String memerId);
}
