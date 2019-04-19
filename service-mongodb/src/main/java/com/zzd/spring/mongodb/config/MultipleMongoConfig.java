package com.zzd.spring.mongodb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * 数据源管理配置
 * Created by Administrator on 2019/4/1 0001.
 */
@Configuration
public class MultipleMongoConfig {
    @Autowired
    private PrimaryMongoConfig primaryMongoConfig;
    @Autowired
    private SecondaryMongoConfig secondaryMongoConfig;

    @Bean()
    public MongoTemplate getPrimaryMongoTemplate() throws Exception {
        return primaryMongoConfig.getMongoTemplate();
    }

    @Bean()
    public MongoTemplate getSecondaryMongoTemplate() throws Exception {
        return secondaryMongoConfig.getMongoTemplate();
    }

}
