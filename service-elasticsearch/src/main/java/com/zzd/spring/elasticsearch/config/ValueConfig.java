package com.zzd.spring.elasticsearch.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * 配置文件相关属性配置注入
 * 
 * @author zhangzhende
 * @version 2018年11月5日
 * @see ValueConfig
 * @since
 */
@Configuration
public class ValueConfig {

    /**
     * 集群名称
     */
    @Value("${ES.index}")
    private String index;

    /**
     * 超时时间
     */
    @Value("${ES.timeDifference}")
    private long timeDifference;

    /**
     * 数据批次大小
     */
    @Value("${ES.batchSize}")
    private int batchSize;
    public String getIndexName() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public long getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(long timeDifference) {
        this.timeDifference = timeDifference;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

}
