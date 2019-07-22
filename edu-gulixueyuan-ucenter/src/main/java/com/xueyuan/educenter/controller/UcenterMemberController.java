package com.xueyuan.educenter.controller;


import com.xueyuan.educenter.entity.UcenterMember;
import com.xueyuan.educenter.service.UcenterMemberService;
import com.xueyuan.educenter.utils.JwtUtils;
import com.xueyuan.educommon.entity.R;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-07-16
 */
@RestController
@RequestMapping("/educenter/ucenter-member")
@Api(description = "用户管理")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    //通过token进行登录
    @GetMapping("goLogin/{token}")
    public R goLogin(@PathVariable String token){

        Claims claims = JwtUtils.checkJWT(token);

        String id = claims.get("id", String.class);

        String nickname = claims.get("nickname", String.class);

        String avatar = claims.get("avatar", String.class);

        UcenterMember member = new UcenterMember();
        member.setId(id);
        member.setNickname(nickname);
        member.setAvatar(avatar);

        return R.ok().data("member",member);
    }


    @ApiOperation(value = "当天注册人数")
    @GetMapping("ucenterCount/{day}")
    public R getRegisterCount(@PathVariable("day") String day){

        Integer num = ucenterMemberService.getRegisterNum(day);

        return R.ok().data("count",num);
    }




}

