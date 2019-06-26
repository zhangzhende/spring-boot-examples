package com.zzd.restclient.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 说明类的用途
 * @ClassName EsValueConfig
 * @Author zzd
 * @Create 2019/6/24 10:32
 * @Version 1.0
 **/
@Component
@ConfigurationProperties(prefix = "esconfig")
public class EsValueConfig implements Serializable {

    private static final long serialVersionUID = 5445982596698324459L;
    private String clustername;


    private List<Hosts> hosts;

    public String getClustername() {
        return clustername;
    }

    public void setClustername(String clustername) {
        this.clustername = clustername;
    }


    public List<Hosts> getHosts() {
        return hosts;
    }

    public void setHosts(List<Hosts> hosts) {
        this.hosts = hosts;
    }
}
