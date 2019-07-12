package com.zzd.spring.demoone.client;

import com.zzd.spring.demoone.entity.Test;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class Demo3FeignHystric implements Demo3Feign {


    @Override
    public List<Test> list() {
        System.out.println("进入断路器-list。。。");
        throw new RuntimeException("list 保存失败.");
    }

    @Override
    public int save() {
        System.out.println("进入断路器-save。。。");
        throw new RuntimeException("save 保存失败.");
    }
}
