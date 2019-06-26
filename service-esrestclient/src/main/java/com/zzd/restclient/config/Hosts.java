package com.zzd.restclient.config;

import java.io.Serializable;

/**
 * @Description 说明类的用途
 * @ClassName Hosts
 * @Author zzd
 * @Create 2019/6/24 10:37
 * @Version 1.0
 **/
public class Hosts implements Serializable {

    private static final long serialVersionUID = 4877168863962393499L;
    private String ip;

    private Integer port;

    private String schema;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
