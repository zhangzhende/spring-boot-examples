package com.zzd.spring.elasticsearch.config;


import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangzhende
 * @version 2018年11月6日
 * @see ElasticSearchConfig
 * @since
 */
@Configuration
public class ElasticSearchConfig {

    /**
     * 
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchConfig.class);

    /**
     * 集群名称
     */
    @Value("${elasticSearch.cluster}")
    private String cluster;

    /**
     * 服务地址
     */
    @Value("${elasticSearch.host}")
    private String host;

    /**
     * 端口
     */
    @Value("${elasticSearch.port}")
    private int port;

    /**
     * 单例模式
     */
    private static TransportClient CLIENT = null;

    /**
     * 
     *获取实例化
     * 
     * @return TransportClient
     * @throws UnknownHostException 
     * @see
     */
    @SuppressWarnings("resource")
    public TransportClient getInstance()
        throws UnknownHostException {
        if (CLIENT == null) {
            Settings settings = Settings.builder().put("cluster.name", cluster).put("node.name",
                "node-1").put("client.transport.sniff", true).build();
            CLIENT = new PreBuiltTransportClient(settings).addTransportAddress(
                new InetSocketTransportAddress(InetAddress.getByName(host), port));
            return CLIENT;
        }
        return CLIENT;
    }

    /**
     * 
     * 监控所用
     * 
     * @return BulkProcessor
     * @throws UnknownHostException 
     * @see
     */
    @Bean
    public BulkProcessor bulkProcessor()
        throws UnknownHostException {
        TransportClient client = getInstance();
        BulkProcessor.Listener listener = new BulkProcessor.Listener() {

            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                LOGGER.info("before bulk");
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                LOGGER.info("bulk 出现异常时");
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                LOGGER.info(" bulk 成功之后");
            }
        };
        return BulkProcessor.builder(client, listener).setBulkActions(10000)// 设置请求数量超过10000词将触发批量提交动作
        .setBulkSize(new ByteSizeValue(20, ByteSizeUnit.MB))// 设置批量请求达到20M触发批量提交操作
        .setFlushInterval(TimeValue.timeValueSeconds(5))// 设置刷新索引的时间间隔
        .setConcurrentRequests(5)// 设置并发线程个数
        .setBackoffPolicy(
            BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3)).build();// 设置回滚策略
    }
}
