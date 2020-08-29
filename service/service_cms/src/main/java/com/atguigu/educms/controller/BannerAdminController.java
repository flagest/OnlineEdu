package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 后台管理的banner表
 * </p>
 *
 * @author zhengWu
 * @since 2020-08-01
 */
//@CrossOrigin
@RestController
@RequestMapping("/educms/banneradmin")
@Api(description = "后台管理的banner表")
public class BannerAdminController {
    @Resource
    private CrmBannerService bannerAdminService;



    @ApiOperation(value = "获取Banner分页列表")
    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit) {
        IPage<CrmBanner> crmBannerIPage = bannerAdminService.page(new Page<>(page, limit), null);
        return R.ok().data("total", crmBannerIPage.getTotal()).data("items", crmBannerIPage.getRecords());
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = bannerAdminService.getById(id);
        return R.ok().data("item", banner);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("save")
    public R save(@RequestBody CrmBanner banner) {
        bannerAdminService.save(banner);
        return R.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public R updateById(@RequestBody CrmBanner banner) {
        bannerAdminService.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        bannerAdminService.removeById(id);
        return R.ok();
    }
}

