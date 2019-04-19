package com.zzd.spring.alipay.config.wechat;


import java.io.InputStream;


/**
 * 微信支付配置类
 * 
 */
public class WXPayConfig {
    /** appID */
    private String appID;

    /** mchID */
    private String mchID;

    /** key */
    private String key;

    /** 密钥文件 */
    private InputStream certStream;

    /** httpConnectTimeoutMs */
    private int httpConnectTimeoutMs;

    /** httpReadTimeoutMs */
    private int httpReadTimeoutMs;

    /** 字符集 */
    private String charSet;

    /** mch_appid */
    private String mchAppid;

    public String getMchAppid() {
        return mchAppid;
    }

    public void setMchAppid(String mchAppid) {
        this.mchAppid = mchAppid;
    }

    public String getCharSet() {
        return charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getMchID() {
        return mchID;
    }

    public void setMchID(String mchID) {
        this.mchID = mchID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public InputStream getCertStream() {
        return certStream;
    }

    public void setCertStream(InputStream certStream) {
        this.certStream = certStream;
    }

    public int getHttpConnectTimeoutMs() {
        return httpConnectTimeoutMs;
    }

    public void setHttpConnectTimeoutMs(int httpConnectTimeoutMs) {
        this.httpConnectTimeoutMs = httpConnectTimeoutMs;
    }

    public int getHttpReadTimeoutMs() {
        return httpReadTimeoutMs;
    }

    public void setHttpReadTimeoutMs(int httpReadTimeoutMs) {
        this.httpReadTimeoutMs = httpReadTimeoutMs;
    }

    @Override
    public String toString() {
        return "WXPayConfig [appID=" + appID + ", mchID=" + mchID + ", key=" + key
               + ", certStream=" + certStream + ", httpConnectTimeoutMs=" + httpConnectTimeoutMs
               + ", httpReadTimeoutMs=" + httpReadTimeoutMs + ", charSet=" + charSet + "]";
    }

}
