package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantProVodUtils;
import com.atguigu.vod.utils.InitObject;
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
        String title = fileName.substring(0, fileName.lastIndexOf("."));
        String videoId = null;
        try {
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantProVodUtils.ACCESSKEY_ID
                    , ConstantProVodUtils.ACCESSKEY_SECRET, title, fileName, inputStream);

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

        LOGGER.info("文件视频ID为  " + videoId);
        return R.ok().data("videoId", videoId);
    }

    @Override
    public R deleteVideoById(String videoSourceId) {
        String requestId = null;
        try {
            DefaultAcsClient client = InitObject.initVodClient(ConstantProVodUtils.ACCESSKEY_ID, ConstantProVodUtils.ACCESSKEY_SECRET);
            DeleteVideoResponse response = this.deleteVideo(client, videoSourceId);
            requestId = response.getRequestId();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuLiException(20001, "删除阿里云视频出错了:(");
        }
        LOGGER.info("请求Id为  " + requestId);
        return R.ok();
    }

    /**
     * 删除视频
     *
     * @param client 发送请求客户端
     * @return DeleteVideoResponse 删除视频响应数据
     * @throws Exception
     */
    public static DeleteVideoResponse deleteVideo(DefaultAcsClient client, String videoSourceId) throws Exception {
        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(videoSourceId);
        return client.getAcsResponse(request);

    }

}
