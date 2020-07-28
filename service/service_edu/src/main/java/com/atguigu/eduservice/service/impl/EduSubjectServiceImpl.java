package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author zhengWu
 * @since 2020-07-15
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
private static final Logger LOGGER = LoggerFactory.getLogger(EduSubjectServiceImpl.class);
    @Override
    public R saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class, new SubjectListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuLiException(20002, "Excel上传出错:(");
        }
        return R.ok();
    }

    //    使用递归去解决要比下面这个方法性能要好一些
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询所有
        long startTime = System.currentTimeMillis();
        List<EduSubject> EduSubjects = baseMapper.selectList(null);
        List<OneSubject> listSub = findListSub(EduSubjects);
        LOGGER.info("执行时间为[{}]ms!", System.currentTimeMillis() - startTime);
        return listSub;
    }

    //父节点
    private List<OneSubject> findListSub(List<EduSubject> EduSubjects) {
        List<OneSubject> oneParentSubjects = new ArrayList<>();
        for (EduSubject eduSubject : EduSubjects) {
            if ("0".equals(eduSubject.getParentId())) {
                OneSubject oneSubject = new OneSubject();
                BeanUtils.copyProperties(eduSubject, oneSubject);
                oneParentSubjects.add(oneSubject);
            }
        }
        for (OneSubject oneSubject : oneParentSubjects) {
            oneSubject.setChildren(findChildder(oneSubject.getId(), EduSubjects));
        }
        return oneParentSubjects;
    }

    //孩子节点
    private List<OneSubject> findChildder(String id, List<EduSubject> eduSubjects) {
        List<OneSubject> oneChildernSubjects = new ArrayList<>();
        for (EduSubject eduSubject : eduSubjects) {
            if (id.equals(eduSubject.getParentId())) {
                OneSubject oneSubject = new OneSubject();
                BeanUtils.copyProperties(eduSubject, oneSubject);
                oneChildernSubjects.add(oneSubject);
            }
        }
        for (OneSubject oneSubject : oneChildernSubjects) {
            oneSubject.setChildren(findChildder(oneSubject.getId(), eduSubjects));
        }
        if (oneChildernSubjects.size() == 0) {
            return null;
        }
        return oneChildernSubjects;
    }


  /*
  这个代码是根据老师视频敲上去的，我觉得如下代码扩展性不高，我就用上面的递归方法这样方便后面扩展
  @Override
    public List<OneSubject> getAllOneTwoSubject() {
        long startTime = System.currentTimeMillis();
        //一级分类
        LambdaQueryWrapper<EduSubject> laQueryEduOne = new QueryWrapper<EduSubject>().lambda();
        laQueryEduOne.eq(EduSubject::getParentId, "0");
        List<EduSubject> oneEduSubjects = baseMapper.selectList(laQueryEduOne);

        //二级分类
        LambdaQueryWrapper<EduSubject> laQuerEduTwo = new LambdaQueryWrapper<>();
        laQuerEduTwo.ne(EduSubject::getParentId, "0");
        List<EduSubject> twoEduSubjects = baseMapper.selectList(laQuerEduTwo);
        //创建List集合封装最终的数据
        ArrayList<OneSubject> finalSubjects = new ArrayList<>();
        for (int i = 0; i < oneEduSubjects.size(); i++) {
            OneSubject oneSubject = new OneSubject();
            EduSubject oneEduSubject = oneEduSubjects.get(i);
            BeanUtils.copyProperties(oneEduSubject, oneSubject);
            finalSubjects.add(oneSubject);
            ArrayList<TwoSubject> twoSubjects = new ArrayList<>();
            for (int t = 0; t < twoEduSubjects.size(); t++) {
                EduSubject twoEduSubject = twoEduSubjects.get(t);
                if (twoEduSubject.getParentId().equals(oneEduSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(twoEduSubject, twoSubject);
                    twoSubjects.add(twoSubject);

                }
            }
            oneSubject.setChildren(twoSubjects);
        }
        log.info("执行时间为[{}]ms!", System.currentTimeMillis() - startTime);
        return finalSubjects;
    }*/
}
