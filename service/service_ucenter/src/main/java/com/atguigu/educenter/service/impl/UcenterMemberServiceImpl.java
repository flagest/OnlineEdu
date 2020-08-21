package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.educenter.VO.LoginVO;
import com.atguigu.educenter.VO.RegisterVO;
import com.atguigu.educenter.client.MsmClinet;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.MD5;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zhengWu
 * @since 2020-08-05
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Resource
    private UcenterMemberMapper ucenterMemberMapper;
    @Resource
    private MsmClinet msmClinet;


    @Override
    public R register(RegisterVO registerVO) {
        String code = registerVO.getCode();
        String phone = registerVO.getMobile();
        String nickName = registerVO.getNickname();
        String password = registerVO.getPassword();
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(phone)
                || StringUtils.isEmpty(nickName) || StringUtils.isEmpty(password))
            throw new GuLiException(20001, "注册失败:(");
        R result = msmClinet.veifingCode(registerVO.getMobile(), registerVO.getCode());
        if (!result.isSuccess())
            throw new GuLiException(20001, "验证码有误:(");
        LambdaQueryWrapper<UcenterMember> laQueryUcenter = new QueryWrapper<UcenterMember>().lambda();
        laQueryUcenter.eq(UcenterMember::getMobile, phone);
        if (ucenterMemberMapper.selectCount(laQueryUcenter) > 0)
            throw new GuLiException(20001, "该号码已注册:(");
        UcenterMember ucenterMember = new UcenterMember();
        BeanUtils.copyProperties(registerVO, ucenterMember);
        ucenterMember.setPassword(MD5.encrypt(registerVO.getPassword()));

        int effNum = ucenterMemberMapper.insert(ucenterMember);

        return retBool(effNum) ? R.ok() : R.error();
    }

    @Override
    public R findMemberInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        LambdaQueryWrapper<UcenterMember> laQueryWarpUceter = new QueryWrapper<UcenterMember>().lambda();
        laQueryWarpUceter.select(UcenterMember::getSex, UcenterMember::getAvatar, UcenterMember::getAge,
                UcenterMember::getId, UcenterMember::getNickname, UcenterMember::getMobile)
                .eq(UcenterMember::getId, memberId);
        UcenterMember ucenterMember = ucenterMemberMapper.selectOne(laQueryWarpUceter);
        if (StringUtils.isEmpty(ucenterMember))
            throw new GuLiException(20001, "该用户没有注册:(");
        return R.ok().data("userInfo", ucenterMember);
    }

    @Override
    public R login(LoginVO loginVO) {
        String mobile = loginVO.getMobile();
        String password = loginVO.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password))
            throw new GuLiException(20001, "用户名或者密码为空:(");
        LambdaQueryWrapper<UcenterMember> laQueryWarpUceter = new QueryWrapper<UcenterMember>().lambda();
        laQueryWarpUceter.select(UcenterMember::getPassword, UcenterMember::getId, UcenterMember::getNickname)
                .eq(UcenterMember::getMobile, mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(laQueryWarpUceter);
        if (StringUtils.isEmpty(ucenterMember))
            throw new GuLiException(20001, "没有该用户:(");
        if (!MD5.encrypt(password).equals(ucenterMember.getPassword()))
            throw new GuLiException(20001, "用户密码错误:(");
        String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return R.ok().data("token", jwtToken);
    }

    @Override
    public int countRegister(String day) {
        return ucenterMemberMapper.countRegister(day);
    }
}
