package com.atguigu.vod.service;

import com.atguigu.commonutils.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wu on 2020/7/26 0026
 */
public interface VodService {


    R uploadAlyiVideo(MultipartFile file);

    R deleteVideoById(String videoSourceId);

    R getPlayAuth(String videoId);
}
