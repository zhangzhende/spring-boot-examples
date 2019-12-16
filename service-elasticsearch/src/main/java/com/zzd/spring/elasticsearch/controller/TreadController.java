package com.zzd.spring.elasticsearch.controller;

import com.zzd.spring.elasticsearch.config.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 说明类的用途
 * @ClassName TreadController
 * @Author zzd
 * @Date 2019/12/4 16:36
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/demo")
public class TreadController {

    @Autowired
    private AsyncService asyncServicesImpl;
    @RequestMapping(value = "/test")
    public String getTest(){
        asyncServicesImpl.executeAsync();
        return "ok";
    }
}
