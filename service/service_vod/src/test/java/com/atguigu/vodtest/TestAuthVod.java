package com.atguigu.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

/**
 * @author wu on 2020/7/26 0026
 */
public class TestAuthVod {
    public static void main(String[] args)   {
        try {
            DefaultAcsClient client = InitObject.initVodClient(""
                    , "");
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId("");

            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            System.out.println("ResponeAuth=  "+response.getPlayAuth()+"\n");
            System.out.println("VideoMeta.Title = " +response.getVideoMeta().getTitle()+"\n");
          /*  int i=4;
            int a=i/0;*/
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage()+"===");
        }

    }
}
