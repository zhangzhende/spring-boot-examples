package com.zzd.spring.demoone.controller;

import com.example.demo.entity.TTest;
import com.example.demo.service.TTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (TTest)表控制层
 *
 * @author makejava
 * @since 2019-07-11 19:48:59
 */
@RestController
@RequestMapping("tTest")
public class TTestController {
    /**
     * 服务对象
     */
    @Resource
    private TTestService tTestService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public TTest selectOne(Integer id) {
        return this.tTestService.queryById(id);
    }

}