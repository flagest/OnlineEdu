package com.atguigu.educenter.mapper;

import com.atguigu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author zhengWu
 * @since 2020-08-05
 */
@Mapper
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    int countRegister(@Param("day") String day);
}
