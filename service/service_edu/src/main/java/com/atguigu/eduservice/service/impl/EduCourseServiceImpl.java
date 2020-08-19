package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.OrderClient;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduCourseDescriptionMapper;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.eduservice.utils.ValidationUtil;
import com.atguigu.eduservice.vo.ChapterVO;
import com.atguigu.eduservice.vo.CourseInfoVO;
import com.atguigu.eduservice.vo.CoursePublishVO;
import com.atguigu.eduservice.vo.CourseSerachVO;
import com.atguigu.eduservice.vo.frontvo.CourseFrontVO;
import com.atguigu.eduservice.vo.frontvo.CourseWebVO;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EduCourseServiceImpl.class);
    @Resource
    private EduCourseDescriptionMapper eduCourseDescriptionMapper;
    @Resource
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Resource
    private EduCourseMapper eduCourseMapper;
    @Resource
    private EduVideoService eduVideoService;
    @Resource
    private EduChapterService eduChapterService;
    @Resource
    private VodClient vodClient;

    @Resource
    private OrderClient orderClient;


    @Override
    @CacheEvict(value = "coursesAndTeachers", allEntries = true)
    public R addCourseInfo(CourseInfoVO courseInfoVO) {
        int insertCourse = 0;
        int insertCourseDesc = 0;
        EduCourse eduCourse;
        String courseId = "";


        try {
            eduCourse = new EduCourse();
            BeanUtils.copyProperties(courseInfoVO, eduCourse);
            insertCourse = baseMapper.insert(eduCourse);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuLiException(20001, "保存课程信息出错了:(");
        }
        courseId = eduCourse.getId();
        try {
            EduCourseDescription eduCourseDescription = new EduCourseDescription();
            BeanUtils.copyProperties(courseInfoVO, eduCourseDescription);
            eduCourseDescription.setId(courseId);
            insertCourseDesc = eduCourseDescriptionMapper.insert(eduCourseDescription);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuLiException(20002, "保存课程描述信息出错了:(");
        }
        return (insertCourse + insertCourseDesc) >= 2 ? R.ok().data("courseId", courseId) : R.error();
    }


    //根据课程id查询课程基本信息
    @Override
    public CourseInfoVO getCourseInfo(String courseId) {
        //1 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVO courseInfoVo = new CourseInfoVO();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        //2 查询描述表
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        if (!StringUtils.isEmpty(courseDescription))
            courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    //修改课程信息
    @Override
    @CacheEvict(value = "coursesAndTeachers", allEntries = true)
    public void updateCourseInfo(CourseInfoVO courseInfoVo) {
        //1 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0)
            throw new GuLiException(20001, "修改课程信息失败");


        //2 修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(description);
    }

    @Override
    public R getPublishCourseInfo(String courseId) {
        CoursePublishVO coursePublishVO = eduCourseMapper.getPublishCourseInfo(courseId);
        if (StringUtils.isEmpty(coursePublishVO))
            throw new GuLiException(20001, "没有该课程！");

        return R.ok().data("publishCourse", coursePublishVO);
    }

    @Override
    public R getCourseList(long current, long limit, CourseSerachVO courseSerachVO) {
        Page<EduCourse> eduCoursePage = new Page<>(current, limit);
        LambdaQueryWrapper<EduCourse> laEduCourseList = new QueryWrapper<EduCourse>().lambda();
        laEduCourseList.select(EduCourse::getId, EduCourse::getTitle, EduCourse::getStatus
                , EduCourse::getPrice, EduCourse::getGmtCreate, EduCourse::getViewCount, EduCourse::getLessonNum)
                .like(!StringUtils.isEmpty(courseSerachVO.getTitle()), EduCourse::getTitle, courseSerachVO.getTitle())
                .eq(!StringUtils.isEmpty(courseSerachVO.getStatus()), EduCourse::getStatus, courseSerachVO.getStatus())
                .ge(!StringUtils.isEmpty(courseSerachVO.getBegin()), EduCourse::getGmtCreate, courseSerachVO.getBegin())
                .le(!StringUtils.isEmpty(courseSerachVO.getEnd()), EduCourse::getGmtModified, courseSerachVO.getEnd())
                .orderByDesc(EduCourse::getGmtCreate);
        IPage<EduCourse> eduCourseIPage = eduCourseMapper.selectPage(eduCoursePage, laEduCourseList);
        HashMap<String, Object> eduHashMap = new HashMap<>();
        eduHashMap.put("total", eduCourseIPage.getTotal());
        eduHashMap.put("items", eduCourseIPage.getRecords());
        return R.ok().data(eduHashMap);
    }

    @Override
    public R coursesAndOthers(String id) {
        try {
            //根据课程id删除小节
            LambdaQueryWrapper<EduVideo> lambdaEduvideo = new QueryWrapper<EduVideo>().lambda();
            lambdaEduvideo.select(EduVideo::getId, EduVideo::getVideoSourceId).eq(EduVideo::getCourseId, id);
            List<EduVideo> listEduvideo = eduVideoService.list(lambdaEduvideo);
            List<String> listVideoAliynIds = new ArrayList<>();
            List<String> listEduVideoIds = new ArrayList<>();

            listEduvideo.stream().forEach((EduVideo eduVideo) -> {
                listEduVideoIds.add(eduVideo.getId());
                String videoSourceId = eduVideo.getVideoSourceId();
                if (!StringUtils.isEmpty(videoSourceId))
                    listVideoAliynIds.add(videoSourceId);
            });

            boolean resDeleEduVideo = eduVideoService.removeByIds(listEduVideoIds);
          /*
           方法一集合转为String
           StringBuilder sb = new StringBuilder();
            listVideoAliynIds.stream().forEach((String aliyunVideId) -> {
                sb.append(aliyunVideId).append(",");
            });
              //删除阿里云视频
            R r = vodClient.deleteVideo(sb.substring(0, sb.length() - 1));
            */
            //方法二集合转为String1
            String stringAliyunIds = org.apache.commons.lang.StringUtils.join(listVideoAliynIds.toArray(), ",");

            //删除阿里云视频
            if (!StringUtils.isEmpty(stringAliyunIds)) {
                R r = vodClient.deleteVideo(stringAliyunIds);
                if (!r.isSuccess())
                    throw new GuLiException(20001, "删除阿里云视频失败:(");
            }
            //根据课程id删除章节
            LambdaQueryWrapper<EduChapter> lambdaEduChapter = new QueryWrapper<EduChapter>().lambda();
            lambdaEduChapter.select(EduChapter::getId).eq(EduChapter::getCourseId, id);
            List<EduChapter> listEduChapter = eduChapterService.list(lambdaEduChapter);
            List<String> listEduChapterIds = new ArrayList<>();
            listEduChapter.stream().forEach(eduChapter -> listEduChapterIds.add(eduChapter.getId()));
            boolean resDeleEduChapter = eduChapterService.removeByIds(listEduChapterIds);

            //更具课程id删除描述
            boolean resDeleCourDes = eduCourseDescriptionService.removeById(id);

            //根据课程id删除本身
            int resDeleCourse = baseMapper.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuLiException(20001, "删除课程中出错:(");
        }
        return R.ok();
    }

    @Override
    public R getFrontCourseList(long page, long limit, CourseFrontVO courseFrontVO) {
        Page<EduCourse> coursePages = new Page<>(page, limit);
        LambdaQueryWrapper<EduCourse> laQueryEduCourse = new LambdaQueryWrapper<>();
        laQueryEduCourse
                .eq(!StringUtils.isEmpty(courseFrontVO.getSubjectParentId()), EduCourse::getSubjectParentId, courseFrontVO.getSubjectParentId())
                .eq(!StringUtils.isEmpty(courseFrontVO.getSubjectId()), EduCourse::getSubjectId, courseFrontVO.getSubjectId())
                .orderByDesc(!StringUtils.isEmpty(courseFrontVO.getBuyCountSort()), EduCourse::getBuyCount)
                .orderByDesc(!StringUtils.isEmpty(courseFrontVO.getGmtCreateSort()), EduCourse::getGmtCreate)
                .orderByDesc(!StringUtils.isEmpty(courseFrontVO.getPriceSort()), EduCourse::getPrice);
        IPage<EduCourse> eduCourseIPage = eduCourseMapper.selectPage(coursePages, laQueryEduCourse);
        Map<String, Object> mapTeachers = new HashMap<>();
        mapTeachers.put("items", coursePages.getRecords());
        mapTeachers.put("current", coursePages.getCurrent());
        mapTeachers.put("total", coursePages.getTotal());
        mapTeachers.put("size", coursePages.getSize());
        mapTeachers.put("pages", coursePages.getPages());
        mapTeachers.put("hasPrevious", coursePages.hasPrevious());
        mapTeachers.put("next", coursePages.hasNext());
        return R.ok().data(mapTeachers);
    }

    @Override
    public R getCoursesInfo(String courseId, HttpServletRequest request) {
        CourseWebVO courseWebVO = eduCourseMapper.getBaseCourseInfo(courseId);
        List<ChapterVO> chapterVideoList = eduChapterService.getChapterVideoByCourseId(courseId);
        //根据课程id和用户id查询单前课程是否支付
        boolean isBuy = orderClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));
        LOGGER.info("isBuy"+isBuy);
        return R.ok().data("courseWebVO", courseWebVO).data("chapterVideoList", chapterVideoList)
                .data("isBuy", isBuy);
    }

}
