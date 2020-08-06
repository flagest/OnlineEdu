package com.atguigu.educenter.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author wu on 2020/8/5 0005
 */
@FeignClient(name = "service-msm")
public interface MsmClinet {

    @GetMapping("/edumsm/msm/veifing/{phone}/{code}")
    public R veifingCode(@PathVariable("phone") String phone,
                         @PathVariable("code") String code);
}
