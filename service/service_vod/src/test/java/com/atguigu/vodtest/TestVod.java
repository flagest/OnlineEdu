package com.atguigu.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;

import java.util.List;

/**
 * @author wu on 2020/7/26 0026
 */
public class TestVod {
    @Value("${aliyun.oss.file.keyid}")
    private String accessKeyId;
    @Value("${aliyun.oss.file.keysecret}")
    private String accessKeySecret;


    public static void main(String[] args) throws Exception {
        testUploadVideo();

    }

    private static void getVideoInfo() throws ClientException {
        DefaultAcsClient defaultAcsClient = InitObject.initVodClient("LTAI4FjWa9vboNKj7NxGpLTW"
                , "deCyHlvBqywIa6LOjl8XP8WLBPcHkR");
//        GetPlayInfoResponse response = new GetPlayInfoResponse();
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("52118bce8e5d4dc9ae3415f3f29a41c1");
        GetPlayInfoResponse response = defaultAcsClient.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        playInfoList.forEach(one -> System.out.println("Play info URL  " + one.getPlayURL() + "\n"));
        System.out.println("VideoBaseTitle" + response.getVideoBase().getTitle() + "\n");
    }

    public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client) throws ClientException {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("52118bce8e5d4dc9ae3415f3f29a41c1");
        return client.getAcsResponse(request);

    }

    //测试视频上传
    public static void testUploadVideo() throws Exception {
        //1.音视频上传-本地文件上传
        //视频标题(必选)
        String title = "测试上传视频";
        //本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
        //文件名必须包含扩展名
        String fileName = "D:\\2bad5d271de54723b782bdb57cd81fcf.mp4";
        UploadVideoRequest request = new UploadVideoRequest("LTAI4FjWa9vboNKj7NxGpLTW"
                , "deCyHlvBqywIa6LOjl8XP8WLBPcHkR", title, fileName);

        /* 可指定分片上传时每个分片的大小，默认为1M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        /* 是否开启断点续传, 默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上传请求，可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。
        注意: 断点续传开启后，会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速度，请您根据实际情况选择是否开启*/
        request.setEnableCheckpoint(false);
        UploadVideoImpl uploadVideo = new UploadVideoImpl();
        UploadVideoResponse response = uploadVideo.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

}
