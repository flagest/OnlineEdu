package com.atguigu.oss.service.serviceImpl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.atguigu.commonutils.R;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import com.atguigu.oss.service.VodService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author wu on 2020/7/26 0026
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class VodServiceImpl implements VodService {
private static final Logger LOGGER = LoggerFactory.getLogger(VodServiceImpl.class);
    @Override
    public R uploadAlyiVideo(MultipartFile file) {

        String fileName = file.getOriginalFilename();
        String title =fileName.substring(0,fileName.lastIndexOf("."));
        String videoId = null;
        try {
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtils.ACCESSKEY_ID
                    , ConstantPropertiesUtils.ACCESSKEY_SECRET, title, fileName, inputStream);

            UploadVideoImpl uploadVideo = new UploadVideoImpl();
            UploadStreamResponse response = uploadVideo.uploadStream(request);
            videoId = response.getVideoId();
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuLiException(20001, "读取上传视频出错了:(");
        } catch (Exception e) {
            e.getLocalizedMessage();
            throw new GuLiException(20001, "上传视频出错了:(");
        }

        LOGGER.info("文件视频ID为  "+videoId);
        return R.ok().data("videoId", videoId);
    }
}
