package com.atguigu.eduservice.controller.front;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.service.EduCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author zhengWu
 * @since 2020-08-15
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/commentfront")
@Api(description = "这是给前端展示评论的页面功能接口")
public class CommentFrontController {
    @Resource
    private EduCommentService commentService;

    @GetMapping("/commentlist/{page}/{limit}/{courseId}")
    @ApiOperation("根据page，limit获取评论列表")
    public R getCommentList(@PathVariable long page
            , @PathVariable long limit, @PathVariable String courseId) {
        return commentService.getCommentList(page, limit, courseId);
    }


    @PostMapping("/addcomments")
    @ApiOperation("用户传入评论信息")
    public R addcomments(@RequestBody EduComment eduComment,HttpServletRequest request) {
        return commentService.addcomments(eduComment,request);
    }
}

