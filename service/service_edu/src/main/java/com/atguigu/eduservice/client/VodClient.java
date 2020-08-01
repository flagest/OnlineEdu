package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.impl.VodClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 视频远程调用接口
 *
 * @author wu on 2020/7/27 0027
 */
@Component
@FeignClient(name = "service-vod",fallback = VodClientImpl.class)
public interface VodClient {

    //调用视频删除接口
    @DeleteMapping("/eduvod/video/{videoSourceId}")
    public R deleteVideo(@PathVariable("videoSourceId") String videoSourceId);
}
