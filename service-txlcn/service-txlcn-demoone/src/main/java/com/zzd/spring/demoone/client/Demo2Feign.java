package com.zzd.spring.demoone.client;

import com.zzd.spring.demoone.entity.Test;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by lorne on 2017/6/27.
 */
@FeignClient(value = "demo2",fallback = Demo2FeignHystric.class)
public interface Demo2Feign {


    @RequestMapping(value = "/demo/list",method = RequestMethod.GET)
    List<Test> list();


    @RequestMapping(value = "/demo/save",method = RequestMethod.GET)
    int save();
}
