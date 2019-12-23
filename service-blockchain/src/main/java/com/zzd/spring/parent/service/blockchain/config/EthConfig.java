package com.zzd.spring.parent.service.blockchain.config;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;

import java.util.concurrent.TimeUnit;

/**
 * @Description 说明类的用途
 * @ClassName EthConfig
 * @Author zzd
 * @Date 2019/12/10 18:49
 * @Version 1.0
 **/
@Configuration
public class EthConfig {

    @Value("${web3j.client-address}")
    private String rpcUrl;

    private final static long CONNECT_TIMEOUT = 30 * 1000;

    @Bean
    public Web3j web3j() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.writeTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.readTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        OkHttpClient okHttpClient = builder.build();
        Web3j web3j = Web3j.build(new HttpService(rpcUrl, okHttpClient, false));
        return web3j;
    }

    @Bean
    public Admin admin(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.writeTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.readTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        OkHttpClient okHttpClient = builder.build();
        Admin admin = Admin.build(new HttpService(rpcUrl, okHttpClient, false));
        return admin;
    }
}
