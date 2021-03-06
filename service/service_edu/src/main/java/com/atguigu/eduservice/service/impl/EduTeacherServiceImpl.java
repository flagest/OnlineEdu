package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.emnu.ResultEnum;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.eduservice.vo.CoursesAndTeachersVO;
import com.atguigu.eduservice.vo.TeacherQuery;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-04
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {
    @Resource
    private EduTeacherMapper eduTeacherMapper;
    @Resource
    private EduCourseMapper eduCourseMapper;

    @Override
    public R pageCondition(long current, long limit, TeacherQuery teacherQuery) {
        Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);
        LambdaQueryWrapper<EduTeacher> teacherLambdaWrapper = new QueryWrapper<EduTeacher>().lambda();
    /*
      第一种方法使用Lambda
      LambdaQueryWrapper<EduTeacher> teacherLambdaWrapper = Wrappers.<EduTeacher>lambdaQuery();
      第二种方法使用Lambda
        LambdaQueryWrapper<EduTeacher> teacherLambdaWrapper = new LambdaQueryWrapper<>();*/
        teacherLambdaWrapper
                .like(!StringUtils.isEmpty(teacherQuery.getName()), EduTeacher::getName, teacherQuery.getName())
                .eq(!StringUtils.isEmpty(teacherQuery.getLevel()), EduTeacher::getLevel, teacherQuery.getLevel())
                .ge(!StringUtils.isEmpty(teacherQuery.getBegin()), EduTeacher::getGmtCreate, teacherQuery.getBegin())
                .le(!StringUtils.isEmpty(teacherQuery.getEnd()), EduTeacher::getGmtModified, teacherQuery.getEnd())
                .orderByDesc(EduTeacher::getGmtCreate);
        IPage<EduTeacher> eduTeacherIPage = eduTeacherMapper.selectPage(eduTeacherPage, teacherLambdaWrapper);
        Map<String, Object> techerHashMap = new HashMap<>();
        techerHashMap.put("total", eduTeacherIPage.getTotal());
        techerHashMap.put("items", eduTeacherIPage.getRecords());

       /* try {
            int i = 10 / 0;
        } catch (Exception e) {
            throw new GuLiException(ResultEnum.ERROR.code(), ResultEnum.ERROR.message());
        }*/
        return R.ok().data(techerHashMap);
    }

    @Override
    public R pageListTeacher(long current, long limit) {
        Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);
        IPage<EduTeacher> eduTeacherIPage = eduTeacherMapper.selectPage(eduTeacherPage, null);
        Map<String, Object> techerHashMap = new HashMap<>();
        techerHashMap.put("total", eduTeacherIPage.getTotal());
        techerHashMap.put("items", eduTeacherIPage.getRecords());
        return R.ok().data(techerHashMap);
    }

    @Override
    @CacheEvict(value = "coursesAndTeachers", allEntries = true)
    public R saveTeacherMessage(EduTeacher teacher) {
        if (teacher.getName() == null || "".equals(teacher.getName()))
            throw new GuLiException(ResultEnum.NAME_NOTNULL.code(), ResultEnum.NAME_NOTNULL.message());
      /* Validate.notNull(teacher.getName(),"添加教师姓名不能为空！");
        Assert.hasText(teacher.getName(),"添加教师姓名不能为空！");*/
        try {
//            return this.retBool()eduTeacherMapper.insert(teacher) >= 1 ? R.ok() : R.error();
            return this.retBool(eduTeacherMapper.insert(teacher)) ? R.ok() : R.error();
        } catch (DataIntegrityViolationException e) {
            log.info("保存讲师信息失败！" + "讲师姓名" + teacher.getName() + "已重复");
            throw new GuLiException(104, "讲师姓名  " + teacher.getName() + "  已重复");
        }
    }

    @Override
    @Cacheable(key = "'coursesAndTeachers'", value = "coursesAndTeachersVO")
    public CoursesAndTeachersVO getTeachersAndCourses() {
        //返回前8个讲师
        LambdaQueryWrapper<EduCourse> laWraEduCourse = new LambdaQueryWrapper<>();
        laWraEduCourse.orderByDesc(EduCourse::getId).last("limit 8");
        //返回前4个课程
        LambdaQueryWrapper<EduTeacher> laWraEduTeacher = new LambdaQueryWrapper<>();
        laWraEduTeacher.orderByDesc(EduTeacher::getId).last("limit 4");
        CoursesAndTeachersVO coursesAndTeachersVO = new CoursesAndTeachersVO();
        coursesAndTeachersVO.setCourseList(eduCourseMapper.selectList(laWraEduCourse));
        coursesAndTeachersVO.setTeacherList(eduTeacherMapper.selectList(laWraEduTeacher));
        return coursesAndTeachersVO;
    }

    @Override
    public R selectByPage(long page, long limit) {
        LambdaQueryWrapper<EduTeacher> laQueryEduTeacher = new QueryWrapper<EduTeacher>().lambda();
        laQueryEduTeacher.orderByDesc(EduTeacher::getId).select(EduTeacher::getId
                , EduTeacher::getName, EduTeacher::getIntro, EduTeacher::getCareer
                , EduTeacher::getAvatar);
        Page<EduTeacher> eduTeacherPage = new Page<>(page, limit);
        eduTeacherMapper.selectPage(eduTeacherPage, laQueryEduTeacher);
        Map<String, Object> mapTeachers = new HashMap<>();
        mapTeachers.put("items", eduTeacherPage.getRecords());
        mapTeachers.put("current", eduTeacherPage.getCurrent());
        mapTeachers.put("total", eduTeacherPage.getTotal());
        mapTeachers.put("size", eduTeacherPage.getSize());
        mapTeachers.put("pages", eduTeacherPage.getPages());
        mapTeachers.put("hasPrevious", eduTeacherPage.hasPrevious());
        mapTeachers.put("next", eduTeacherPage.hasNext());
        return R.ok().data(mapTeachers);
    }

    @Override
    public R getTeacherInfo(String teacherId) {
        LambdaQueryWrapper<EduTeacher> laQueryTeacher = new QueryWrapper<EduTeacher>().lambda();
        laQueryTeacher.eq(EduTeacher::getId, teacherId)
                .select(EduTeacher.class, info -> !info.getColumn().equals("gmt_create")
                        && !info.getColumn().equals("gmt_modified")
                        && !info.getColumn().equals("is_deleted")
                        && !info.getColumn().equals("sort"));
        EduTeacher eduTeacher = eduTeacherMapper.selectOne(laQueryTeacher);
        if (StringUtils.isEmpty(eduTeacher))
            throw new GuLiException(20001, "没有该讲师信息:(");
        LambdaQueryWrapper<EduCourse> laQueryCourse = new QueryWrapper<EduCourse>().lambda();
        laQueryCourse.eq(EduCourse::getTeacherId, eduTeacher.getId()).select(EduCourse.class,
                info -> !info.getColumn().equals("gmt_create")
                        && !info.getColumn().equals("gmt_modified")
                        && !info.getColumn().equals("is_deleted")
                        && !info.getColumn().equals("status")
                        && !info.getColumn().equals("version"));
        List<EduCourse> eduCourseList = eduCourseMapper.selectList(laQueryCourse);
        return R.ok().data("eduTeacher", eduTeacher).data("courseList", eduCourseList);
    }


}
