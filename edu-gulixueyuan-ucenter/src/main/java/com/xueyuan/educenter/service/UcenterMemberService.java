package com.xueyuan.educenter.service;

import com.xueyuan.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-07-16
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    Integer getRegisterNum(String day);

    UcenterMember getByOpenId(String openId);
}
