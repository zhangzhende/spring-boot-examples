package com.zzd.demotwo.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;

import com.zzd.demotwo.dao.TestMapper;
import com.zzd.demotwo.entity.Test;
import com.zzd.demotwo.service.DemoService;
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
@Service
public class DemoServiceImpl implements DemoService {

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

        int rs = testMapper.save("mybatis-hello-2", new Date());
        logger.info("插入" + rs + "条记录");
        return rs;
    }
}
