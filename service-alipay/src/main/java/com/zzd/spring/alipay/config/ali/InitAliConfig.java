package com.zzd.spring.alipay.config.ali;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Administrator on 2019/4/8 0008.
 */
@Configuration
public class InitAliConfig implements Serializable, ResourceLoaderAware {

    private static final Logger LOG = LoggerFactory.getLogger(InitAliConfig.class);
    private static final long serialVersionUID = 3937533896797490016L;

    /**
     * 资源加载
     */
    private ResourceLoader resourceLoader;
    /**
     * appid
     */
    @Value("${pay.ali.appId}")
    private String appId;
    /**
     * 异步通知地址
     */
    @Value("${pay.ali.notifyUrl}")
    private String notifyUrl;
    /**
     * 响应地址
     */
    @Value("${pay.ali.returnUrl}")
    private String returnUrl;
    /**
     * 支付宝网关地址
     */
    @Value("${pay.ali.url}")
    private String url;
    /**
     * 字符集
     */
    @Value("${pay.ali.charset}")
    private String charset;
    /**
     * 传输格式xml/json
     */
    @Value("${pay.ali.format}")
    private String format;
    /**
     * 公钥
     */
    @Value("${pay.ali.aliPayPublicKey}")
    private String aliPayPublicKey;
    /**
     * 私钥
     */
    @Value("${pay.ali.rsaPrivateKey}")
    private String rsaPrivateKey;
    /**
     * 日志位置
     */
    @Value("${pay.ali.logPath}")
    private String logPath;
    /**
     * 加密方式
     */
    @Value("${pay.ali.singType}")
    private String singType;
    /**
     * 支付结果通知url
     */
    @Value("${pay.ali.payBackUrl}")
    private String payBackUrl;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getAliPayPublicKey() {
        return aliPayPublicKey;
    }

    public void setAliPayPublicKey(String aliPayPublicKey) {
        this.aliPayPublicKey = aliPayPublicKey;
    }

    public String getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public String getSingType() {
        return singType;
    }

    public void setSingType(String singType) {
        this.singType = singType;
    }

    public String getPayBackUrl() {
        return payBackUrl;
    }

    public void setPayBackUrl(String payBackUrl) {
        this.payBackUrl = payBackUrl;
    }

    @Bean
    public AlipayConfig initConfig() {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setAppId(appId);
        alipayConfig.setCharset(charset);
        alipayConfig.setFormat(format);
        alipayConfig.setLogPath(logPath);
        alipayConfig.setNotifyUrl(notifyUrl);
        alipayConfig.setReturnUrl(returnUrl);
        alipayConfig.setSingType(singType);
        alipayConfig.setUrl(url);
        alipayConfig.setAliPayPublicKey(readFile2String(aliPayPublicKey));
        alipayConfig.setRsaPrivateKey(readFile2String(rsaPrivateKey));
        alipayConfig.setPayBackUrl(payBackUrl);
        return alipayConfig;
    }
    @Override
    public String toString() {
        return "AlipayConfig{" +
                "appId='" + appId + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                ", url='" + url + '\'' +
                ", charset='" + charset + '\'' +
                ", format='" + format + '\'' +
                ", aliPayPublicKey='" + aliPayPublicKey + '\'' +
                ", rsaPrivateKey='" + rsaPrivateKey + '\'' +
                ", logPath='" + logPath + '\'' +
                ", singType='" + singType + '\'' +
                ", payBackUrl='" + payBackUrl + '\'' +
                '}';
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 加载类路径下资源 Description: <br> 1、…<br> 2、…<br> Implement: <br> 1、…<br> 2、…<br>
     *
     * @param location
     * @return 资源
     * @see
     */
    public Resource getResource(String location) {
        return resourceLoader.getResource(location);
    }

    private String readFile2String(String location) {
        try {
            return IOUtils.toString(getResource(location).getInputStream(), charset);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            System.exit(0);
        }
        return null;
    }
}
