package com.atguigu.eduservice.service;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author zhengWu
 * @since 2020-07-15
 */
public interface EduSubjectService extends IService<EduSubject> {

    public R saveSubject(MultipartFile file, EduSubjectService subjectService);

    public List<OneSubject> getAllOneTwoSubject();


}
