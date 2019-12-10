package com.zzd.spring.parent.seetaface.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Description 项目启动时加载
 * @ClassName ApplicationStartupRunner
 * @Author zzd
 * @Date 2019/12/2 14:02
 * @Version 1.0
 **/
@Component
public class ApplicationStartupRunner implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationStartupRunner.class);
    @Override
    public void run(String... args) throws Exception {
        LOG.info("===============项目初始加载===========================");
        SeetafaceBuilder.build();
        LOG.info("===============数据库初始加载===========================");
        SeetafaceBuilder.loadFaceDb();
        LOG.info("===============数据库初始加载完成===========================");
    }
}
