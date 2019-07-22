package com.xueyuan.educenter.mapper;

import com.xueyuan.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2019-07-16
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer getRegisterCount(String day);

}
