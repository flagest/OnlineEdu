package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.impl.UcenterClientImpl;
import com.atguigu.eduservice.client.impl.VodClientImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wu on 2020/8/15 0015
 */
@Component
@FeignClient(name = "service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {

//    , fallback = UcenterClientImpl.class
    @GetMapping("/api/ucenter/member/getMemberInfo")
    R getMemberInfo(@Param("request") HttpServletRequest request);


}
