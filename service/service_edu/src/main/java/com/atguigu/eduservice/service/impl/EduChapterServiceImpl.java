package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.eduservice.vo.ChapterVO;
import com.atguigu.eduservice.vo.VideoVO;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zhengWu
 * @since 2020-07-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;//注入小节service

    @Resource
    private EduVideoMapper eduVideoMapper;

    //课程大纲列表,根据课程id进行查询
    @Override
    public List<ChapterVO> getChapterVideoByCourseId(String courseId) {

        //1 根据课程id查询课程里面所有的章节
        LambdaQueryWrapper<EduChapter> laQWEduCha = new QueryWrapper<EduChapter>().lambda();

        laQWEduCha.select(EduChapter::getId, EduChapter::getCourseId, EduChapter::getTitle).eq(EduChapter::getCourseId, courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(laQWEduCha);

        //2 根据课程id查询课程里面所有的小节
        LambdaQueryWrapper<EduVideo> laQWEduVideo = new QueryWrapper<EduVideo>().lambda();
        laQWEduVideo.select(EduVideo::getId, EduVideo::getChapterId, EduVideo::getCourseId, EduVideo::getTitle)
                .eq(EduVideo::getCourseId, courseId);
        List<EduVideo> eduVideoList = videoService.list(laQWEduVideo);

        //创建list集合，用于最终封装数据
        List<ChapterVO> finalList = new ArrayList<>();

        //3 遍历查询章节list集合进行封装
        //遍历查询章节list集合
        for (int i = 0; i < eduChapterList.size(); i++) {
            //每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            //eduChapter对象值复制到ChapterVo里面
            ChapterVO chapterVo = new ChapterVO();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            //把chapterVo放到最终list集合
            finalList.add(chapterVo);

            //创建集合，用于封装章节的小节
            List<VideoVO> videoList = new ArrayList<>();

            //4 遍历查询小节list集合，进行封装
            for (int m = 0; m < eduVideoList.size(); m++) {
                //得到每个小节
                EduVideo eduVideo = eduVideoList.get(m);
                //判断：小节里面chapterid和章节里面id是否一样
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    //进行封装
                    VideoVO videoVo = new VideoVO();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    //放到小节封装集合
                    videoList.add(videoVo);
                }
            }
            //把封装之后小节list集合，放到章节对象里面
            chapterVo.setChildren(videoList);
        }
        return finalList;
    }

    @Override
    public R deleteChapter(String chapterId) {
        LambdaQueryWrapper<EduVideo> laQwper = new QueryWrapper<EduVideo>().lambda();
        laQwper.eq(EduVideo::getCourseId, chapterId);
        int countResult = videoService.count(laQwper);
        if (countResult > 0)
            throw new GuLiException(20001, "章节中有小节不能删除:(");
        int result = eduVideoMapper.deleteById(chapterId);
//        int result = baseMapper.deleteById(chapterId);

        return result > 0 ? R.ok() : R.error();

    }

}
