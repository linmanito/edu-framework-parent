package com.xueyuan.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xueyuan.educenter.entity.UcenterMember;
import com.xueyuan.educenter.mapper.UcenterMemberMapper;
import com.xueyuan.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-07-16
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Override
    public Integer getRegisterNum(String day) {
        return baseMapper.getRegisterCount(day);
    }

    @Override
    public UcenterMember getByOpenId(String openId) {

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openId);

        UcenterMember member = baseMapper.selectOne(wrapper);


        return member;
    }
}
