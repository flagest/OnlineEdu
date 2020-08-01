package com.atguigu.educms.service;

import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author zhengWu
 * @since 2020-08-01
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectList();

}
