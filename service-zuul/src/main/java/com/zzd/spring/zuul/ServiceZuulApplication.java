package com.zzd.spring.zuul;

import com.zzd.spring.zuul.filter.GloblePostZuulfilter;
import com.zzd.spring.zuul.filter.GloblePreLevel2ZuulFilter;
import com.zzd.spring.zuul.filter.GloblePreZuulfilter;
import com.zzd.spring.zuul.filter.GlobleRouteZuulfilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class ServiceZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceZuulApplication.class, args);
    }

    //	配置filter
    @Bean
    public GloblePreZuulfilter globlePreZuulfilter() {
        return new GloblePreZuulfilter();
    }

    @Bean
    public GloblePreLevel2ZuulFilter globlePreLevel2ZuulFilter() {
        return new GloblePreLevel2ZuulFilter();
    }

    @Bean
    public GlobleRouteZuulfilter globleRouteZuulfilter() {
        return new GlobleRouteZuulfilter();
    }

    @Bean
    public GloblePostZuulfilter globlePostZuulfilter(){
        return new GloblePostZuulfilter();
    }
}
