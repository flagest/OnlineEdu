package com.atguigu.educenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.ConstantsWxUtils;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author wu on 2020/8/8 0008
 */
@Slf4j
@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
@Transactional(rollbackFor = Exception.class)
public class WxApiController {
    @Resource
    private UcenterMemberService memberService;

    //获取扫码人的信息方便存储
    @GetMapping("callback")
    public String callback(String code, String state) {
        log.info(code);
        log.info(state);
        //向认证服务器发送请求换取access_token
        try {
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接三个参数 ：id  秘钥 和 code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantsWxUtils.WX_OPEN_APP_ID,
//                    "wxed9954c01bb89b47",
                    ConstantsWxUtils.WX_OPEN_APP_SECRET,
//                    "a7482517235173ddb4083788de60b90e",
                    code

            );
            //请求这个拼接好的地址，得到返回两个值 accsess_token 和 openid
            //使用httpclient发送请求，得到返回结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);

            //从accessTokenInfo字符串获取出来两个值 accsess_token 和 openid
            //把accessTokenInfo字符串转换map集合，根据map里面key获取对应值
            //使用json转换工具 Gson
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");
            LambdaQueryWrapper<UcenterMember> laQueryMember = new QueryWrapper<UcenterMember>().lambda();
            laQueryMember.eq(UcenterMember::getOpenid, openid).select(UcenterMember::getOpenid
                    , UcenterMember::getId, UcenterMember::getNickname);
            UcenterMember ucenterMember = memberService.getOne(laQueryMember);
            if (StringUtils.isEmpty(ucenterMember)) {
                ucenterMember = new UcenterMember();
                log.info("是新用户" + openid);
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                //拼接两个参数
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );

                String resultUserInfo = HttpClientUtils.get(userInfoUrl);

                //解析json
                HashMap<String, Object> mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
                String nickname = (String) mapUserInfo.get("nickname");
                String headimgurl = (String) mapUserInfo.get("headimgurl");//头像
            /*    String sex = (String)mapUserInfo.get("sex");
                if (sex!="0.0")
                    ucenterMember.setSex(Integer.valueOf(sex));*/
                ucenterMember.setNickname(nickname);
                ucenterMember.setAvatar(headimgurl);
                ucenterMember.setOpenid(openid);
                boolean resultSave = memberService.save(ucenterMember);
                if (!resultSave)
                    throw new GuLiException(20001, "微信登录保存用户信息失败:(");

            }
            String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
            return "redirect:http://localhost:3000?token="+jwtToken;
        } catch (Exception e) {
            throw new GuLiException(20001, "微信登录信息失败:(");
        }

    }

    //微信二维码登录
    @GetMapping("/login")
    public String getWxCode() {
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //对边URL进行编码

        String redirec_url = ConstantsWxUtils.WX_OPEN_REDIRECT_URL;
        String encodeUrl = "";
        try {
            encodeUrl = URLEncoder.encode(redirec_url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = String.format(baseUrl
                , ConstantsWxUtils.WX_OPEN_APP_ID
                , encodeUrl
                , "atguigu");

        return "redirect:" + url;
    }
}
