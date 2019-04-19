package com.zzd.spring.alipay.config.ali;


import org.hibernate.validator.constraints.NotBlank;


/**
 * 支付宝配置信息
 * 
 */
public class AlipayConfig {

    /**
     * 商户appid
     */
    @NotBlank
    private String appId;

    /**
     * 私钥
     */
    @NotBlank
    private String rsaPrivateKey;

    /**
     * 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    @NotBlank
    private String notifyUrl;

    /**
     * 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    // 商户可以自定义同步跳转地址
    private String returnUrl;

    /**
     * 请求网关地址
     */
    @NotBlank
    private String url;

    /**
     * 编码
     */
    @NotBlank
    private String charset;

    /**
     * 返回格式
     */
    @NotBlank
    private String format;

    /**
     * 公钥
     */
    @NotBlank
    private String aliPayPublicKey;

    /**
     * 日志记录目录
     */
    private String logPath;

    /**
     * RSA2
     */
    @NotBlank
    private String singType;

    /**
     * 支付结果通知地址
     */
    private String payBackUrl;

    public String getPayBackUrl() {
        return payBackUrl;
    }

    public void setPayBackUrl(String payBackUrl) {
        this.payBackUrl = payBackUrl;
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
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

    @Override
    public String toString() {
        return "AlipayConfig [appId=" + appId + ", rsaPrivateKey=" + rsaPrivateKey + ", notifyUrl="
               + notifyUrl + ", returnUrl=" + returnUrl + ", url=" + url + ", charset=" + charset
               + ", format=" + format + ", aliPayPublicKey=" + aliPayPublicKey + ", logPath="
               + logPath + ", singType=" + singType + ", payBackUrl=" + payBackUrl + "]";
    }

    

}
