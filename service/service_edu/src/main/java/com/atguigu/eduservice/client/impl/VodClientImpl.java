package com.atguigu.eduservice.client.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

/**
 * @author wu on 2020/7/29 0029
 */
@Component
public class VodClientImpl implements VodClient {
    @Override
    public R deleteVideo(String videoSourceId) {
        return R.error().message("删除视频微服务宕机了:(");
    }
}
