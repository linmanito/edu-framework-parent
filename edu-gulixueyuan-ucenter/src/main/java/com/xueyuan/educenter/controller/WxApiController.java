package com.xueyuan.educenter.controller;


import com.google.gson.Gson;
import com.xueyuan.educenter.entity.UcenterMember;
import com.xueyuan.educenter.service.UcenterMemberService;
import com.xueyuan.educenter.utils.ConstantPropertiesUtil;
import com.xueyuan.educenter.utils.HttpClientUtils;
import com.xueyuan.educenter.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.net.www.http.HttpClient;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WxApiController {


    @Autowired
    private UcenterMemberService ucenterMemberService;





    //授权回调得到token
    @GetMapping("callback")
    public String genQrConnect(String code,String state){

        System.out.println("code : "+code);
        System.out.println("state : "+state);

        try {

            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            String appId = ConstantPropertiesUtil.WX_OPEN_APP_ID;
            String secret = ConstantPropertiesUtil.WX_OPEN_APP_SECRET;
            //进行字符串拼接
            baseAccessTokenUrl=String.format(
                    baseAccessTokenUrl,
                    appId,
                    secret,
                    code
            );


            //向该url发送数据，返回里面包含accessToken和openId
            String result = HttpClientUtils.get(baseAccessTokenUrl);

            System.out.println(result);




            //因为是json所以使用gson解析成Map
            Gson gson = new Gson();
            HashMap map = gson.fromJson(result, HashMap.class);
            //从map中获取openId和accesToken
            String token = (String)map.get("access_token");
            String openId = (String)map.get("openid");

            //查询openid在数据库中是否存在
            UcenterMember member = ucenterMemberService.getByOpenId(openId);

            //判断openId数据库中是否存在，不存在则加入数据库
            if (member == null){
                //再向另一个地址发送请求获得个人信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";

                baseUserInfoUrl = String.format(
                        baseUserInfoUrl,
                        token,
                        openId);

                String result1 = HttpClientUtils.get(baseUserInfoUrl);

                System.out.println("result1: "+result1);

                Map map1 = gson.fromJson(result1, Map.class);
                String nickName = (String)map1.get("nickname");

                String headimgUrl = (String)map1.get("headimgurl");

                member = new UcenterMember();

                member.setOpenid(openId);
                member.setNickname(nickName);
                member.setAvatar(headimgUrl);

                //加入数据库中
                ucenterMemberService.save(member);

            }

            //将member放入jwt中进行授权
            String jwt = JwtUtils.geneJsonWebToken(member);



            return "redirect:http://localhost:3000?token="+jwt ;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @GetMapping("login")
    public String genQrConnect(HttpClient client){


        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect?" +
                "appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        String appId = ConstantPropertiesUtil.WX_OPEN_APP_ID;

        String redirect = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;


        try {
            //对回调地址进行url编码
            redirect = URLEncoder.encode(redirect,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }


        String state = "wxdemo";

        //将上面的baseUrl进行字符串拼接
        String qrcodeUrl = String.format(
                                baseUrl,
                                appId,
                                redirect,
                                state
                                );


        return "redirect:"+qrcodeUrl;


    }

}
