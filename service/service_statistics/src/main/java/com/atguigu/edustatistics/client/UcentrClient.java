package com.atguigu.edustatistics.client;

import com.atguigu.edustatistics.client.impl.UcenterClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author wu on 2020/8/20 0020
 */
@Component
@FeignClient(name = "service-ucenter", fallback = UcenterClientImpl.class)
public interface UcentrClient {

    @GetMapping("/api/ucenter/member/countRegister/{day}")
    int countRegister(@PathVariable("day") String day);
}
