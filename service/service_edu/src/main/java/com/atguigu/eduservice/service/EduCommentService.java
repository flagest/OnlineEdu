package com.atguigu.eduservice.service;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author zhengWu
 * @since 2020-08-15
 */
public interface EduCommentService extends IService<EduComment> {

    R getCommentList(long page, long limit,String courseId);

    R addcomments(EduComment eduComment,HttpServletRequest request);
}
