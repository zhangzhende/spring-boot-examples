package com.zzd.spring.demoone.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;

import com.zzd.spring.demoone.client.Demo2Feign;
import com.zzd.spring.demoone.client.Demo3Feign;
import com.zzd.spring.demoone.dao.TestMapper;
import com.zzd.spring.demoone.entity.Test;
import com.zzd.spring.demoone.service.TDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by lorne on 2017/6/26.
 */
@Service("tDemoService")
public class DemoServiceImpl implements TDemoService {


    @Autowired
    private Demo2Feign demo2Feign;

    @Autowired
    private Demo3Feign demo3Feign;


    @Autowired
    private TestMapper testMapper;

    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Override
    public List<Test> list() {
        return testMapper.findAll();
    }

    @Override
    @LcnTransaction //分布式事务注解
    @Transactional
    public int save() {

        int rs1 = testMapper.save("mybatis-hello-1", new Date());
        logger.info("本地服务数据插入成功");
        int rs2 = demo2Feign.save();
        logger.info("远程服务2号数据插入成功");
        int rs3 = demo3Feign.save();
        logger.info("远程服务3号数据插入成功");
        logger.info("插入" + (rs1 + rs2 + rs3) + "条记录");
        logger.info("制造异常");
        int v = 100 / 0;

        return rs1 + rs2 + rs3;
    }
}
