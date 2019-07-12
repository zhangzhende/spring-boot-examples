package com.zzd.spring.demoone.controller;


import com.example.demo.entity.Test;
import com.example.demo.service.TDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lorne on 2017/6/26.
 */
@RestController
@RequestMapping(value = "/demo")
public class DemoController {

    @Autowired
    private TDemoService tDemoService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Test> list(){
        System.out.println("$$$$$$$$$$$$$$$$$$$");
        return tDemoService.list();
    }


    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public int save(){
        return tDemoService.save();
    }
}
