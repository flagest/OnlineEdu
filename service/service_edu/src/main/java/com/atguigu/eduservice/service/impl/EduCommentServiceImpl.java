package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.UcenterClient;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.mapper.EduCommentMapper;
import com.atguigu.eduservice.service.EduCommentService;
import com.atguigu.servicebase.dto.UcenterMemberDTO;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author zhengWu
 * @since 2020-08-15
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {
    @Resource
    private EduCommentMapper eduCommentMapper;

    @Resource
    private UcenterClient ucenterClient;

    @Resource
    private HttpServletRequest request;

    @Override
    public R getCommentList(long page, long limit, String courseId) {
        Page<EduComment> eduCommentPages = new Page<EduComment>(page, limit);
        LambdaQueryWrapper<EduComment> laQueryEduComment = new LambdaQueryWrapper<>();
        laQueryEduComment.eq(EduComment::getCourseId, courseId)
                .select(EduComment.class,
                        info -> !info.getColumn().equals("gmt_modified")
                                && !info.getColumn().equals("is_deleted"));
        eduCommentMapper.selectPage(eduCommentPages, laQueryEduComment);
        Map<String, Object> commentsMap = new HashMap<>();
        commentsMap.put("items", eduCommentPages.getRecords());
        commentsMap.put("current", eduCommentPages.getCurrent());
        commentsMap.put("total", eduCommentPages.getTotal());
        commentsMap.put("size", eduCommentPages.getSize());
        commentsMap.put("pages", eduCommentPages.getPages());
        commentsMap.put("hasPrevious", eduCommentPages.hasPrevious());
        commentsMap.put("next", eduCommentPages.hasNext());
        return R.ok().data(commentsMap);
    }

    @Override
    public R addcomments(EduComment eduComment) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId))
            throw new GuLiException(20001, "请登录在做评论:(");
        UcenterMemberDTO ucenterMemberDTO = ucenterClient.getUserInfoOrder(memberId);
        if (StringUtils.isEmpty(eduComment.getCourseId()) || StringUtils.isEmpty(eduComment.getTeacherId()))
            throw new GuLiException(20001, "请传入课程或讲师信息:(");
        eduComment.setMemberId(ucenterMemberDTO.getId());
        eduComment.setNickname(ucenterMemberDTO.getNickname());
        eduComment.setAvatar(ucenterMemberDTO.getAvatar());
        int effNum = eduCommentMapper.insert(eduComment);
        return retBool(effNum) ? R.ok() : R.error().message("保存会员评论失败:(");
    }
}
