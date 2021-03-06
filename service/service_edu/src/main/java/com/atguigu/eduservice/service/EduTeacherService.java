package com.atguigu.eduservice.service;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.vo.CoursesAndTeachersVO;
import com.atguigu.eduservice.vo.TeacherQuery;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-04
 */
public interface EduTeacherService extends IService<EduTeacher> {


    /**
     * @param * @param null
     * @Date:11:10 2020/7/5 0005
     * @Description:多条件查询并分页
     * @retrun
     */
    public R pageCondition(long current, long limit, TeacherQuery teacherQuery);

    /**
     * @param * @param null
     * @Date:11:14 2020/7/5 0005
     * @Description:查询所有并分页
     * @retrun
     */
    public R pageListTeacher(long current, long limit);

    /**
     * @param * @param null
     * @Date:22:01 2020/7/13 0013
     * @Description:保存讲师信息
     * @retrun
     */
    public R saveTeacherMessage(EduTeacher teacher);

    /**
     * @param * @param null
     * @Date:22:02 2020/8/1 0001
     * @Description:前台前端页面要求返回结果为8个降序排列的课程4位降序排列的教师
     * @retrun
     */
    public CoursesAndTeachersVO getTeachersAndCourses();


    R selectByPage(long page, long limit);

    R getTeacherInfo(String teacherId);
}
