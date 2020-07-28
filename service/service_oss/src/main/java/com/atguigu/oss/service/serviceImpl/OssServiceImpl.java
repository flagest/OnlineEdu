package com.atguigu.oss.service.serviceImpl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import static com.atguigu.oss.emnu.ResultEnum.UPLOAD_ERROR;


/**
 * @author wu on 2020/7/12 0012
 */
@Slf4j
@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFile(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.ACCESSKEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESSKEY_SECRET;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            String fileNameUUID = UUID.randomUUID().toString().replaceAll("-", "");
            String depthPath = new DateTime().toString("yyyy/MM/dd");
            fileName=depthPath+"/"+fileNameUUID+fileName;
            ossClient.putObject(ConstantPropertiesUtils.BUCKET_NAME, fileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            String url = "https://" + ConstantPropertiesUtils.BUCKET_NAME + "." + endpoint + "/" + fileName;
            log.info(url);
            return url;
        } catch (Exception e) {
            throw new GuLiException(UPLOAD_ERROR.code(), UPLOAD_ERROR.message());
        }
    }
}
