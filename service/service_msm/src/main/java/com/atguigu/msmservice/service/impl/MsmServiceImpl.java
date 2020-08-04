package com.atguigu.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @author wu on 2020/8/4 0004
 */
@Service
public class MsmServiceImpl implements MsmService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsmServiceImpl.class);

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public R send(String phone) {
        String code = RandomUtil.getFourBitRandom();
        LOGGER.info(code + "发送的code是！！！！");
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        if (StringUtils.isEmpty(phone)) return R.error().message("验证码为空:(");

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "*****"
                        , "******");
        IAcsClient client = new DefaultAcsClient(profile);

        //设置关键参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送相关参数
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "陈晨购物商城");
        request.putQueryParameter("TemplateCode", "SMS_184215727");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(params));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            if (response.getHttpResponse().isSuccess()) {
                redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
                return R.ok().data("SMSCode", code);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuLiException(20001, "验证码发送失败:(");
        }
        return R.error();
    }

    @Override
    public R veifing(String phone, String code) {
        String redisCode = (String) redisTemplate.opsForValue().get(phone);
        LOGGER.info(redisCode + " Rdis中存储的Code是！！");
        if (!StringUtils.isEmpty(redisCode)) {
            if (code.equals(redisCode))
                return R.ok();
            return R.error().message("验证码错误:(");
        }
        return R.error().message("请重新申请验证码");
    }
}
