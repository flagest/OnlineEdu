package com.atguigu.oss.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wu on 2020/7/12 0012
 */

public interface OssService {
    String uploadFile(MultipartFile file);
}
