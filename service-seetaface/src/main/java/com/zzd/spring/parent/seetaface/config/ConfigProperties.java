package com.zzd.spring.parent.seetaface.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description 说明类的用途
 * @ClassName ConfigProperties
 * @Author zzd
 * @Date 2019/12/2 14:24
 * @Version 1.0
 **/
@Component
@ConfigurationProperties(prefix = "config")
public class ConfigProperties {
    private String libsPath;

    private String bindadaDir;

    public String getLibsPath() {
        return libsPath;
    }

    public void setLibsPath(String libsPath) {
        this.libsPath = libsPath;
    }

    public String getBindadaDir() {
        return bindadaDir;
    }

    public void setBindadaDir(String bindadaDir) {
        this.bindadaDir = bindadaDir;
    }
}
