package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前台用户banner管理
 * </p>
 *
 * @author zhengWu
 * @since 2020-08-01
 */
@CrossOrigin
@RestController
@RequestMapping("/educms/bannerfront")
public class BannerFrontController {

    @Resource
    private CrmBannerService bannerFrontService;


    @GetMapping("getFrontBanners")
    public R getFrontBanner() {
        List<CrmBanner> crmBanners = bannerFrontService.selectList();
        return R.ok().data("bannerList", crmBanners);
    }

}

