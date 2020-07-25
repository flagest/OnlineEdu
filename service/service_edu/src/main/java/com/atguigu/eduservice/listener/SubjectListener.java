package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.eduservice.service.impl.EduSubjectServiceImpl;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.util.StringUtils;

/**
 * @author wu on 2020/7/16 0016
 */
public class SubjectListener extends AnalysisEventListener<SubjectData> {


    public EduSubjectService eduSubjectService;

    public SubjectListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    public SubjectListener() {
    }

    @Override
    public void invoke(SubjectData data, AnalysisContext context) {
        //判断空值
        if (StringUtils.isEmpty(data))
            throw new GuLiException(2001, "表中数据为空");
        //因为要判断是一级分类还是二级分类判定是否相等，
        //一级分类
        EduSubject existOneEduSub = this.oneSubject(eduSubjectService, data.getOneSubjet());
        if (StringUtils.isEmpty(existOneEduSub)) {
            existOneEduSub = new EduSubject();
            existOneEduSub.setParentId("0");
            existOneEduSub.setTitle(data.getOneSubjet());
            eduSubjectService.save(existOneEduSub);
        }
        //二级分类
        String pid = existOneEduSub.getId();
        EduSubject existTwoEduSub = this.twoSubject(eduSubjectService, data.getTwoSubject(), pid);
        if (StringUtils.isEmpty(existTwoEduSub)) {
            existTwoEduSub = new EduSubject();
            existTwoEduSub.setParentId(pid);
            existTwoEduSub.setTitle(data.getTwoSubject());
            eduSubjectService.save(existTwoEduSub);
        }
    }

    //一级分类
    private EduSubject oneSubject(EduSubjectService eduSubjectService, String name) {
        LambdaQueryWrapper<EduSubject> laQWSubject = new QueryWrapper<EduSubject>().lambda();
        laQWSubject.eq(EduSubject::getTitle, name).eq(EduSubject::getParentId, "0");
        EduSubject eduSubject = eduSubjectService.getOne(laQWSubject);
        return eduSubject;
    }

    //二级分类
    private EduSubject twoSubject(EduSubjectService eduSubjectService, String name, String id) {

        LambdaQueryWrapper<EduSubject> laQWSubject = new LambdaQueryWrapper<>();
        laQWSubject.eq(EduSubject::getTitle, name).eq(EduSubject::getParentId, id);
        EduSubject eduSubject = eduSubjectService.getOne(laQWSubject);
        return eduSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
