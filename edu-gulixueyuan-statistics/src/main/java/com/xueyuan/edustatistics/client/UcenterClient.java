package com.xueyuan.edustatistics.client;



import com.xueyuan.educommon.entity.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("guli-ucenter")//指定服务名
@Component
public interface UcenterClient {

    @GetMapping("/educenter/ucenter-member/ucenterCount/{day}")
    public R getRegisterCount(@PathVariable("day") String day);

}
