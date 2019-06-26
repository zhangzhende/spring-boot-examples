package com.zzd.restclient.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 说明类的用途
 * @ClassName ElasticSearchConfig
 * @Author zzd
 * @Create 2019/6/24 10:31
 * @Version 1.0
 **/
@Configuration
public class ElasticSearchConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchConfig.class);

    @Autowired
    private EsValueConfig esValueConfig;


    private static RestHighLevelClient restHighLevelClientclient = null;


    private static RestClient restClient = null;

    /**
     * low level restClient
     *
     * @return
     */
    public RestClient getRestClient() {
        if (restClient != null) {
            return restClient;
        } else {
            synchronized (ElasticSearchConfig.class) {
                HttpHost[] httpHosts = new HttpHost[esValueConfig.getHosts().size()];
                for (int i = 0; i < httpHosts.length; i++) {
                    Hosts host = esValueConfig.getHosts().get(i);
                    httpHosts[i] = new HttpHost(host.getIp(), host.getPort(), host.getSchema());
                }
                RestClientBuilder clientBuilder = RestClient.builder(httpHosts);
                restClient = clientBuilder.build();
                LOGGER.info("RestClient intfo create rest high level client successful!");
                return restClient;
            }
        }
    }


    /**
     * highLevelRestClient
     *
     * @return
     */
    public RestHighLevelClient getRestHighLevelClient() {
        if (restHighLevelClientclient != null) {
            return restHighLevelClientclient;
        } else {
            synchronized (ElasticSearchConfig.class) {
                HttpHost[] httpHosts = new HttpHost[esValueConfig.getHosts().size()];
                for (int i = 0; i < httpHosts.length; i++) {
                    Hosts host = esValueConfig.getHosts().get(i);
                    httpHosts[i] = new HttpHost(host.getIp(), host.getPort(), host.getSchema());
                }
                RestClientBuilder clientBuilder = RestClient.builder(httpHosts);
                restHighLevelClientclient = new RestHighLevelClient(clientBuilder);
                LOGGER.info("RestHighLevelClient intfo create rest high level client successful!");
                return restHighLevelClientclient;
            }
        }
    }

}
