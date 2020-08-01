package com.atguigu.educms.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.mapper.CrmBannerMapper;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author zhengWu
 * @since 2020-08-01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    @Cacheable(key = "'bannerList'", value = "banner")//这个注解是Redis的查询操作
    public List<CrmBanner> selectList() {
        LambdaQueryWrapper<CrmBanner> laQueryCrmBan = new QueryWrapper<CrmBanner>().lambda();
        laQueryCrmBan.orderByDesc(CrmBanner::getId).last("limit 2");
        List<CrmBanner> crmBanners = this.baseMapper.selectList(laQueryCrmBan);
        return crmBanners;
    }
}
