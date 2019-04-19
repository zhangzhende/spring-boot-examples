package com.zzd.spring.alipay.config.wechat;


import java.io.IOException;

import com.zzd.spring.common.utils.WXPayConstants.SignType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;



/**
 * 微信配置信息 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @see InitWXConfig
 * @since
 */
@Configuration
public class InitWXConfig implements ResourceLoaderAware {
    /** 资源加载器 */
    private ResourceLoader resourceLoader;

    /** 日志 */
    private static final Logger LOG = LoggerFactory.getLogger(InitWXConfig.class);

    /** appId */
    @Value("${pay.wechat.appID}")
    private String appID;

    /** mchID */
    @Value("${pay.wechat.mchID}")
    private String mchID;

    /** key */
    @Value("${pay.wechat.key}")
    private String key;

    /** certStreams */
    @Value("${pay.wechat.certStreams}")
    private String certStreams;

    /** httpConnectTimeoutMs */
    @Value("${pay.wechat.httpConnectTimeoutMs}")
    private int httpConnectTimeoutMs;

    /** httpReadTimeoutMs */
    @Value("${pay.wechat.httpReadTimeoutMs}")
    private int httpReadTimeoutMs;

    /** charSet */
    @Value("${pay.wechat.charSet}")
    private String charSet;
    /** charSet */
    @Value("${pay.wechat.mchAppid}")
    private String mchAppid;

    /**
     * 初始化支付 Description: <br> 1、…<br> 2、…<br> Implement: <br> 1、…<br> 2、…<br>
     * 
     * @param wxPayConfig
     * @return WXPay
     * @see
     */
    @Bean
    public WXPay getWXPay(WXPayConfig wxPayConfig) {
        return new WXPay(wxPayConfig, SignType.MD5, true);
    }

    /**
     * 初始化支付信息 Description: <br> 1、…<br> 2、…<br> Implement: <br> 1、…<br> 2、…<br>
     * 
     * @return WXPayConfig
     * @see
     */
    @Bean
    public WXPayConfig getWXConfig() {
        WXPayConfig wxPayConfig = new WXPayConfig();
        wxPayConfig.setAppID(appID);
        wxPayConfig.setCharSet(charSet);
        wxPayConfig.setHttpConnectTimeoutMs(httpConnectTimeoutMs);
        wxPayConfig.setHttpReadTimeoutMs(httpReadTimeoutMs);
        wxPayConfig.setKey(key);
        wxPayConfig.setMchID(mchID);
        wxPayConfig.setMchAppid(mchAppid);
//        try {
////            wxPayConfig.setCertStream(resourceLoader.getResource(certStreams).getInputStream());
//        }
//        catch (IOException e) {
//            LOG.error(e.getMessage(), e);
//            System.exit(0);
//        }
        return wxPayConfig;
    }

    /**
     * 获取资源加载器
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 根据路径加载指定资源文件 Description: <br> 1、…<br> 2、…<br> Implement: <br> 1、…<br> 2、…<br>
     * 
     * @param location
     * @return Resource
     * @see
     */
    public Resource getResource(String location) {
        return resourceLoader.getResource(location);
    }
}
