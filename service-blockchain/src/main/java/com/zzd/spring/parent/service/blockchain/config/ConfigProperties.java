package com.zzd.spring.parent.service.blockchain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Description 配置文件配置信息
 * @ClassName ConfigProperties
 * @Author zzd
 * @Create 2019/6/18 18:28
 * @Version 1.0
 **/
@Component
@ConfigurationProperties(prefix = "config")
public class ConfigProperties implements Serializable {

    /**
     * 钱包地址
     */
    private String keyFilePath;

    /**
     * 密码
     */
    private String password;

    /**
     * 钱包文件名称
     */
    private String keyFileName;

    public String getKeyFileName() {
        return keyFileName;
    }

    public void setKeyFileName(String keyFileName) {
        this.keyFileName = keyFileName;
    }

    public String getKeyFilePath() {
        return keyFilePath;
    }

    public void setKeyFilePath(String keyFilePath) {
        this.keyFilePath = keyFilePath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
