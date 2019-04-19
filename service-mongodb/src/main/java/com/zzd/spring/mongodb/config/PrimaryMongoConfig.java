package com.zzd.spring.mongodb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * 第一个库的数据源封装
 * Created by Administrator on 2019/4/1 0001.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.zzd.spring.mongodb.repository.primary", mongoTemplateRef = PrimaryMongoConfig.MONGO_TEMPLATE)
@ConfigurationProperties(prefix = "mongodb.primary")
public class PrimaryMongoConfig extends AbstractMongoConfigure{
    public static final String MONGO_TEMPLATE="primaryMongoTemplate";

    @Primary
    @Override
    @Bean(name = MONGO_TEMPLATE)
    public MongoTemplate getMongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }
}
