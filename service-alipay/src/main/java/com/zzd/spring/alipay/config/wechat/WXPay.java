package com.zzd.spring.alipay.config.wechat;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.validation.ValidationException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.zzd.spring.common.utils.PayException;
import com.zzd.spring.common.utils.WXPayConstants;
import com.zzd.spring.common.utils.WXPayConstants.SignType;
import com.zzd.spring.common.utils.WXPayConstants.IsCheckName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.google.common.collect.Maps;


/**
 * 〈微信支付核心类〉 〈功能详细描述〉
 * 
 * @version 2018年6月7日
 * @see WXPay
 * @since
 */
public class WXPay {
    /** 微信支付配置类 */
    private WXPayConfig config;

    /** 签名类型 */
    private SignType signType;

    /** 是否是测试环境 */
    private boolean useSandbox;

    /** 日志 */
    private static final Logger LOG = LoggerFactory.getLogger(WXPay.class);

    public WXPay(final WXPayConfig config) {
        this(config, SignType.MD5, false);
    }

    public WXPay(final WXPayConfig config, final SignType signType) {
        this(config, signType, false);
    }

    public WXPay(final WXPayConfig config, final SignType signType, final boolean useSandbox) {
        this.config = config;
        this.signType = signType;
        this.useSandbox = useSandbox;
    }

    public WXPay() {
    }

    /**
     * 向 Map 中添加 appid、mch_id、nonce_str、sign_type、sign <br> 该函数适用于商户适用于统一下单等接口，不适用于红包、代金券接口
     *
     * @param reqData
     * @return Map<String, String>
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws Exception 
     */
    public Map<String, String> fillRequestData(Map<String, String> reqData)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        UnsupportedEncodingException {
        reqData.put("appid", config.getAppID());
        reqData.put("mch_id", config.getMchID());
        reqData.put("nonce_str", WXUtil.generateNonceStr());
        if (SignType.MD5.equals(this.signType)) {
            reqData.put("sign_type", WXPayConstants.MD5);
        }
        else if (SignType.HMACSHA256.equals(this.signType)) {
            reqData.put("sign_type", WXPayConstants.HMACSHA256);
        }
        reqData.put("sign", WXUtil.generateSignature(reqData, config.getKey(), this.signType));
        return reqData;
    }

    /**
     * 判断xml数据的sign是否有效，必须包含sign字段，否则返回false。
     *
     * @param reqData
     *            向wxpay post的请求数据
     * @return 签名是否有效
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws Exception 
     */
    public boolean isResponseSignatureValid(Map<String, String> reqData)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        UnsupportedEncodingException {
        // 返回数据的签名方式和请求中给定的签名方式是一致的
        return WXUtil.isSignatureValid(reqData, this.config.getKey(), this.signType);
    }

    /**
     * 判断支付结果通知中的sign是否有效
     *
     * @param reqData
     *            向wxpay post的请求数据
     * @return 签名是否有效 
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws Exception 
     */
    public boolean isPayResultNotifySignValid(Map<String, String> reqData)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        UnsupportedEncodingException {
        String signTypeInData = reqData.get(WXPayConstants.FIELD_SIGN_TYPE);
        SignType signTypea;
        if (signTypeInData == null) {
            signTypea = SignType.MD5;
        }
        else {
            signTypeInData = signTypeInData.trim();
            if (signTypeInData.length() == 0) {
                signTypea = SignType.MD5;
            }
            else if (WXPayConstants.MD5.equals(signTypeInData)) {
                signTypea = SignType.MD5;
            }
            else if (WXPayConstants.HMACSHA256.equals(signTypeInData)) {
                signTypea = SignType.HMACSHA256;
            }
            else {
                throw new PayException(String.format("Unsupported sign_type: %s", signTypeInData));
            }
        }
        return WXUtil.isSignatureValid(reqData, this.config.getKey(), signTypea);
    }

    /**
     * 不需要证书的请求
     * 
     * @param strUrl
     *            String
     * @param reqData
     *            向wxpay post的请求数据
     * @param connectTimeoutMs
     *            超时时间，单位是毫秒
     * @param readTimeoutMs
     *            超时时间，单位是毫秒
     * @return API返回数据
     * @throws TransformerException 
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws UnsupportedEncodingException 
     */
    public String requestWithoutCert(String strUrl, Map<String, String> reqData,
                                     int connectTimeoutMs, int readTimeoutMs)
        throws ParserConfigurationException,
        TransformerException,
        IOException {
        String reqBody = WXUtil.mapToXml(reqData);
        URL httpUrl = new URL(strUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection)httpUrl.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setConnectTimeout(connectTimeoutMs);
        httpURLConnection.setReadTimeout(readTimeoutMs);
        httpURLConnection.connect();
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(reqBody.getBytes(WXPayConstants.CHARSET));

        if (httpURLConnection.getResponseCode() != 200) {
            throw new ValidationException(String.format("HTTP response code is %d, not200",
                httpURLConnection.getResponseCode()));
        }

        // 获取内容
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(inputStream, WXPayConstants.CHARSET));
        final StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        String resp = sb.toString();
        try {
            bufferedReader.close();
            inputStream.close();
            outputStream.close();
            httpURLConnection.disconnect();
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }

        return resp;
    }

    /**
     * 需要证书的请求
     * 
     * @param strUrl
     *            String
     * @param reqData
     *            向wxpay post的请求数据 Map
     * @param connectTimeoutMs
     *            超时时间，单位是毫秒
     * @param readTimeoutMs
     *            超时时间，单位是毫秒
     * @return API返回数据
     * @throws TransformerException 
     * @throws ParserConfigurationException 
     * @throws KeyStoreException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     * @throws IOException 
     * @throws CertificateException 
     * @throws UnrecoverableKeyException 
     */
    public String requestWithCert(String strUrl, Map<String, String> reqData, int connectTimeoutMs,
                                  int readTimeoutMs)
        throws ParserConfigurationException,
        TransformerException,
        KeyStoreException,
        NoSuchAlgorithmException,
        KeyManagementException,
        IOException,
        CertificateException,
        UnrecoverableKeyException {
        String reqBody = WXUtil.mapToXml(reqData);
        URL httpUrl = new URL(strUrl);
        char[] password = config.getMchID().toCharArray();
        InputStream certStream = config.getCertStream();
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(certStream, password);

        // 实例化密钥库 & 初始化密钥工厂
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(
            KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password);

        // 创建SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        HttpURLConnection httpURLConnection = (HttpURLConnection)httpUrl.openConnection();

        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setConnectTimeout(connectTimeoutMs);
        httpURLConnection.setReadTimeout(readTimeoutMs);
        httpURLConnection.connect();
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(reqBody.getBytes(WXPayConstants.CHARSET));

        if (httpURLConnection.getResponseCode() != 200) {
            throw new ValidationException(String.format("HTTP response code is %d, not200",
                httpURLConnection.getResponseCode()));
        }

        // 获取内容
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(inputStream, WXPayConstants.CHARSET));
        final StringBuilder stringBuffer = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }
        String resp = stringBuffer.toString();
        try {
            bufferedReader.close();
            inputStream.close();
            outputStream.close();
            certStream.close();
            httpURLConnection.disconnect();
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }

        return resp;
    }

    /**
     * 处理 HTTPS API返回数据，转换成Map对象。return_code为SUCCESS时，验证签名。
     * 
     * @param xmlStr
     *            API返回的XML格式数据
     * @return Map类型数据
     * @throws ParserConfigurationException 
     * @throws IOException  
     * @throws SAXException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws Exception 
     */ 
    public Map<String, String> processResponseXml(String xmlStr)
        throws SAXException, 
        IOException,
        ParserConfigurationException,
        InvalidKeyException,
        NoSuchAlgorithmException {
        String returnCodeKey = "return_code";
        String returnCodeValue;
        Map<String, String> respData = WXUtil.xmlToMap(xmlStr);
        if (respData.containsKey(returnCodeKey)) {
            returnCodeValue = respData.get(returnCodeKey);
        }
        else {
            throw new ValidationException(
                String.format("No `returnCodeValue` in XML: %s", xmlStr));
        }

        if (returnCodeValue.equals(WXPayConstants.FAIL)) {
            return respData;
        }
        else if (returnCodeValue.equals(WXPayConstants.SUCCESS)) {
            if (this.isResponseSignatureValid(respData)) {
                return respData;
            }
            else {
                throw new ValidationException(
                    String.format("Invalid sign value in XML: %s", xmlStr));
            }
        }
        else {
            throw new ValidationException(String.format(
                "returnCodeValue value %s is invalid in XML: %s", returnCodeValue, xmlStr));
        }
    }

    /**
     * 作用：提交刷卡支付<br> 场景：刷卡支付
     * 
     * @param reqData
     *            向wxpay post的请求数据
     * @return API返回数据
     * @throws SAXException 
     * @throws IOException 
     * @throws TransformerException 
     * @throws ParserConfigurationException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws Exception 
     */
    public Map<String, String> microPay(Map<String, String> reqData)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        ParserConfigurationException,
        TransformerException,
        IOException,
        SAXException {
        return this.microPay(reqData, this.config.getHttpConnectTimeoutMs(),
            this.config.getHttpReadTimeoutMs());
    }

    /**
     * 作用：提交刷卡支付<br> 场景：刷卡支付
     * 
     * @param reqData
     *            向wxpay post的请求数据
     * @param connectTimeoutMs
     *            连接超时时间，单位是毫秒
     * @param readTimeoutMs
     *            读超时时间，单位是毫秒
     * @return API返回数据
     * @throws IOException 
     * @throws TransformerException 
     * @throws ParserConfigurationException 
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws SAXException 
     * @throws Exception 
     */
    public Map<String, String> microPay(Map<String, String> reqData, int connectTimeoutMs,
                                        int readTimeoutMs)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        ParserConfigurationException,
        TransformerException,
        IOException,
        SAXException {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_MICROPAY_URL;
        }
        else {
            url = WXPayConstants.MICROPAY_URL;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData),
            connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }

    /**
     * 作用：统一下单<br> 场景：公共号支付、扫码支付、APP支付
     * 
     * @param reqData
     *            向wxpay post的请求数据
     * @return API返回数据
     * @throws SAXException 
     * @throws IOException 
     * @throws TransformerException 
     * @throws ParserConfigurationException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws Exception 
     */
    public Map<String, String> unifiedOrder(Map<String, String> reqData)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        ParserConfigurationException,
        TransformerException,
        IOException,
        SAXException {
        return this.unifiedOrder(reqData, config.getHttpConnectTimeoutMs(),
            this.config.getHttpReadTimeoutMs());
    }

    /**
     * 作用：统一下单<br> 场景：公共号支付、扫码支付、APP支付
     * 
     * @param reqData
     *            向wxpay post的请求数据
     * @param connectTimeoutMs
     *            连接超时时间，单位是毫秒
     * @param readTimeoutMs
     *            读超时时间，单位是毫秒
     * @return API返回数据
     * @throws IOException 
     * @throws TransformerException 
     * @throws ParserConfigurationException 
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws SAXException  
     * @throws Exception 
     */
    public Map<String, String> unifiedOrder(Map<String, String> reqData, int connectTimeoutMs,
                                            int readTimeoutMs)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        ParserConfigurationException,
        TransformerException,
        IOException,
        SAXException {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_UNIFIEDORDER_URL;
        }
        else {
            url = WXPayConstants.UNIFIEDORDER_URL;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData),
            connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }

    /**
     * 作用：查询订单<br> 场景：刷卡支付、公共号支付、扫码支付、APP支付
     * 
     * @param reqData
     *            向wxpay post的请求数据
     * @return API返回数据
     * @throws SAXException 
     * @throws IOException 
     * @throws TransformerException 
     * @throws ParserConfigurationException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws Exception 
     */
    public Map<String, String> orderQuery(Map<String, String> reqData)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        ParserConfigurationException,
        TransformerException,
        IOException,
        SAXException {
        return this.orderQuery(reqData, config.getHttpConnectTimeoutMs(),
            this.config.getHttpReadTimeoutMs());
    }

    /**
     * 作用：查询订单<br> 场景：刷卡支付、公共号支付、扫码支付、APP支付
     * 
     * @param reqData
     *            向wxpay post的请求数据 int
     * @param connectTimeoutMs
     *            连接超时时间，单位是毫秒
     * @param readTimeoutMs
     *            读超时时间，单位是毫秒
     * @return API返回数据
     * @throws IOException 
     * @throws TransformerException 
     * @throws ParserConfigurationException 
     * @throws UnsupportedEncodingException  
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException  
     * @throws SAXException 
     * @throws Exception 
     */
    public Map<String, String> orderQuery(Map<String, String> reqData, int connectTimeoutMs,
                                          int readTimeoutMs)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        ParserConfigurationException,
        TransformerException,
        IOException,
        SAXException {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_ORDERQUERY_URL;
        }
        else {
            url = WXPayConstants.ORDERQUERY_URL;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData),
            connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }

    /**
     * 作用：撤销订单<br> 场景：刷卡支付
     * 
     * @param reqData
     *            向wxpay post的请求数据
     * @return API返回数据
     * @throws SAXException 
     * @throws IOException 
     * @throws TransformerException 
     * @throws ParserConfigurationException 
     * @throws CertificateException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyStoreException 
     * @throws InvalidKeyException 
     * @throws UnrecoverableKeyException 
     * @throws KeyManagementException 
     * @throws Exception 
     */
    public Map<String, String> reverse(Map<String, String> reqData)
        throws KeyManagementException,
        UnrecoverableKeyException,
        InvalidKeyException,
        KeyStoreException,
        NoSuchAlgorithmException,
        CertificateException,
        ParserConfigurationException,
        TransformerException,
        IOException,
        SAXException {
        return this.reverse(reqData, config.getHttpConnectTimeoutMs(),
            this.config.getHttpReadTimeoutMs());
    }

    /**
     * 作用：撤销订单<br> 场景：刷卡支付<br> 其他：需要证书
     * 
     * @param reqData
     *            向wxpay post的请求数据
     * @param connectTimeoutMs
     *            连接超时时间，单位是毫秒
     * @param readTimeoutMs
     *            读超时时间，单位是毫秒
     * @return API返回数据
     * @throws IOException 
     * @throws TransformerException 
     * @throws ParserConfigurationException 
     * @throws UnsupportedEncodingException 
     * @throws CertificateException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyStoreException 
     * @throws InvalidKeyException 
     * @throws UnrecoverableKeyException 
     * @throws KeyManagementException 
     * @throws SAXException 
     * @throws Exception 
     */
    public Map<String, String> reverse(Map<String, String> reqData, int connectTimeoutMs,
                                       int readTimeoutMs)
        throws KeyManagementException,
        UnrecoverableKeyException,
        InvalidKeyException,
        KeyStoreException,
        NoSuchAlgorithmException,
        CertificateException,
        ParserConfigurationException,
        TransformerException,
        IOException,
        SAXException {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_REVERSE_URL;
        }
        else {
            url = WXPayConstants.REVERSE_URL;
        }
        String respXml = this.requestWithCert(url, this.fillRequestData(reqData), connectTimeoutMs,
            readTimeoutMs);
        return this.processResponseXml(respXml);
    }

    /**
     * 作用：关闭订单<br> 场景：公共号支付、扫码支付、APP支付
     * 
     * @param reqData
     *            向wxpay post的请求数据
     * @return API返回数据
     * @throws TransformerException 
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws Exception 
     */
    public Map<String, String> closeOrder(Map<String, String> reqData)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        SAXException,
        IOException,
        ParserConfigurationException,
        TransformerException {
        return this.closeOrder(reqData, config.getHttpConnectTimeoutMs(),
            this.config.getHttpReadTimeoutMs());
    }

    /**
     * 作用：关闭订单<br> 场景：公共号支付、扫码支付、APP支付
     * 
     * @param reqData
     *            向wxpay post的请求数据
     * @param connectTimeoutMs
     *            连接超时时间，单位是毫秒
     * @param readTimeoutMs
     *            读超时时间，单位是毫秒
     * @return API返回数据
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws TransformerException 
     * @throws Exception 
     */
    public Map<String, String> closeOrder(Map<String, String> reqData, int connectTimeoutMs,
                                          int readTimeoutMs)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        SAXException,
        IOException,
        ParserConfigurationException,
        TransformerException {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_CLOSEORDER_URL;
        }
        else {
            url = WXPayConstants.CLOSEORDER_URL;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData),
            connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }

    /**
     * 作用：申请退款<br> 场景：刷卡支付、公共号支付、扫码支付、APP支付
     * 
     * @param reqData
     *            向wxpay post的请求数据
     * @return API返回数据
     * @throws TransformerException 
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws CertificateException 
     * @throws KeyStoreException 
     * @throws NoSuchAlgorithmException 
     * @throws UnrecoverableKeyException 
     * @throws KeyManagementException 
     * @throws InvalidKeyException 
     * @throws Exception 
     */
    public Map<String, String> refund(Map<String, String> reqData)
        throws InvalidKeyException,
        KeyManagementException,
        UnrecoverableKeyException,
        NoSuchAlgorithmException,
        KeyStoreException,
        CertificateException,
        SAXException,
        IOException,
        ParserConfigurationException,
        TransformerException {
        return this.refund(reqData, this.config.getHttpConnectTimeoutMs(),
            this.config.getHttpReadTimeoutMs());
    }

    /**
     * 作用：申请退款<br> 场景：刷卡支付、公共号支付、扫码支付、APP支付<br> 其他：需要证书
     * 
     * @param reqData
     *            向wxpay post的请求数据
     * @param connectTimeoutMs
     *            连接超时时间，单位是毫秒
     * @param readTimeoutMs
     *            读超时时间，单位是毫秒
     * @return API返回数据
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws TransformerException 
     * @throws CertificateException 
     * @throws KeyStoreException 
     * @throws UnrecoverableKeyException 
     * @throws KeyManagementException 
     * @throws Exception 
     */
    public Map<String, String> refund(Map<String, String> reqData, int connectTimeoutMs,
                                      int readTimeoutMs)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        SAXException,
        IOException,
        ParserConfigurationException,
        KeyManagementException,
        UnrecoverableKeyException,
        KeyStoreException,
        CertificateException,
        TransformerException {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_REFUND_URL;
        }
        else {
            url = WXPayConstants.REFUND_URL;
        }
        String respXml = this.requestWithCert(url, this.fillRequestData(reqData), connectTimeoutMs,
            readTimeoutMs);
        return this.processResponseXml(respXml);
    }

    /**
     * 作用：退款查询<br> 场景：刷卡支付、公共号支付、扫码支付、APP支付
     * 
     * @param reqData
     *            向wxpay post的请求数据
     * @return API返回数据
     * @throws TransformerException 
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws Exception 
     */
    public Map<String, String> refundQuery(Map<String, String> reqData)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        SAXException,
        IOException,
        ParserConfigurationException,
        TransformerException {
        return this.refundQuery(reqData, this.config.getHttpConnectTimeoutMs(),
            this.config.getHttpReadTimeoutMs());
    }

    /**
     * 作用：退款查询<br> 场景：刷卡支付、公共号支付、扫码支付、APP支付
     * 
     * @param reqData
     *            向wxpay post的请求数据
     * @param connectTimeoutMs
     *            连接超时时间，单位是毫秒
     * @param readTimeoutMs
     *            读超时时间，单位是毫秒
     * @return API返回数据
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws TransformerException 
     * @throws Exception 
     */
    public Map<String, String> refundQuery(Map<String, String> reqData, int connectTimeoutMs,
                                           int readTimeoutMs)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        SAXException,
        IOException,
        ParserConfigurationException,
        TransformerException {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_REFUNDQUERY_URL;
        }
        else {
            url = WXPayConstants.REFUNDQUERY_URL;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData),
            connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }

    /**
     * 作用：对账单下载（成功时返回对账单数据，失败时返回XML格式数据）<br> 场景：刷卡支付、公共号支付、扫码支付、APP支付
     * 
     * @param reqData
     *            向wxpay post的请求数据
     * @return API返回数据
     * @throws ParserConfigurationException 
     * @throws IOException   
     * @throws SAXException 
     * @throws TransformerException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws Exception 
     */
    public Map<String, String> downloadBill(Map<String, String> reqData)
        throws SAXException,
        IOException,
        ParserConfigurationException,
        InvalidKeyException,
        NoSuchAlgorithmException,
        TransformerException {
        return this.downloadBill(reqData, this.config.getHttpConnectTimeoutMs(),
            this.config.getHttpReadTimeoutMs());
    }

    /**
     * 作用：对账单下载<br> 场景：刷卡支付、公共号支付、扫码支付、APP支付<br>
     * 其他：无论是否成功都返回Map。若成功，返回的Map中含有return_code、return_msg、data，
     * 其中return_code为`SUCCESS`，data为对账单数据。
     * 
     * @param reqData
     *            向wxpay post的请求数据
     * @param connectTimeoutMs
     *            连接超时时间，单位是毫秒
     * @param readTimeoutMs
     *            读超时时间，单位是毫秒
     * @return 经过封装的API返回数据
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws TransformerException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws Exception 
     */
    public Map<String, String> downloadBill(Map<String, String> reqData, int connectTimeoutMs,
                                            int readTimeoutMs)
        throws SAXException,
        IOException,
        ParserConfigurationException,
        InvalidKeyException,
        NoSuchAlgorithmException,
        TransformerException {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_DOWNLOADBILL_URL;
        }
        else {
            url = WXPayConstants.DOWNLOADBILL_URL;
        }
        String respStr = this.requestWithoutCert(url, this.fillRequestData(reqData),
            connectTimeoutMs, readTimeoutMs).trim();
        Map<String, String> ret;
        // 出现错误，返回XML数据
        if (respStr.indexOf('<') == 0) {
            ret = WXUtil.xmlToMap(respStr);
        }
        else {
            // 正常返回csv数据
            ret = Maps.newHashMap();
            ret.put("return_code", WXPayConstants.SUCCESS);
            ret.put("return_msg", "ok");
            ret.put("data", respStr);
        }
        return ret;
    }

    /**
     * 作用：交易保障<br> 场景：刷卡支付、公共号支付、扫码支付、APP支付
     * 
     * @param reqData
     *            向wxpay post的请求数据
     * @return API返回数据
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws TransformerException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws Exception 
     */
    public Map<String, String> report(Map<String, String> reqData)
        throws SAXException,
        IOException,
        ParserConfigurationException,
        InvalidKeyException,
        NoSuchAlgorithmException,
        TransformerException {
        return this.report(reqData, this.config.getHttpConnectTimeoutMs(),
            this.config.getHttpReadTimeoutMs());
    }

    /**
     * 作用：交易保障<br> 场景：刷卡支付、公共号支付、扫码支付、APP支付
     * 
     * @param reqData
     *            向wxpay post的请求数据
     * @param connectTimeoutMs
     *            连接超时时间，单位是毫秒
     * @param readTimeoutMs
     *            读超时时间，单位是毫秒
     * @return API返回数据
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws TransformerException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws Exception 
     */
    public Map<String, String> report(Map<String, String> reqData, int connectTimeoutMs,
                                      int readTimeoutMs)
        throws SAXException,
        IOException,
        ParserConfigurationException,
        InvalidKeyException,
        NoSuchAlgorithmException,
        TransformerException {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_REPORT_URL;
        }
        else {
            url = WXPayConstants.REPORT_URL;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData),
            connectTimeoutMs, readTimeoutMs);
        return WXUtil.xmlToMap(respXml);
    }

    /**
     * 作用：转换短链接<br> 场景：刷卡支付、扫码支付
     * 
     * @param reqData
     *            向wxpay post的请求数据
     * @return API返回数据
     * @throws TransformerException 
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws Exception 
     */
    public Map<String, String> shortUrl(Map<String, String> reqData)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        SAXException,
        IOException,
        ParserConfigurationException,
        TransformerException {
        return this.shortUrl(reqData, this.config.getHttpConnectTimeoutMs(),
            this.config.getHttpReadTimeoutMs());
    }

    /**
     * 作用：转换短链接<br> 场景：刷卡支付、扫码支付
     * 
     * @param reqData
     *            向wxpay post的请求数据
     * @return API返回数据
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws TransformerException 
     * @throws Exception 
     */
    public Map<String, String> shortUrl(Map<String, String> reqData, int connectTimeoutMs,
                                        int readTimeoutMs)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        SAXException,
        IOException,
        ParserConfigurationException,
        TransformerException {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_SHORTURL_URL;
        }
        else {
            url = WXPayConstants.SHORTURL_URL;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData),
            connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }

    /**
     * 作用：授权码查询OPENID接口<br> 场景：刷卡支付
     * 
     * @param reqData
     *            向wxpay post的请求数据
     * @return API返回数据
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws TransformerException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws Exception 
     */
    public Map<String, String> authCodeToOpenid(Map<String, String> reqData)
        throws SAXException,
        IOException,
        ParserConfigurationException,
        InvalidKeyException,
        NoSuchAlgorithmException,
        TransformerException {
        return this.authCodeToOpenid(reqData, this.config.getHttpConnectTimeoutMs(),
            this.config.getHttpReadTimeoutMs());
    }

    /**
     * 作用：授权码查询OPENID接口<br> 场景：刷卡支付
     * 
     * @param reqData
     *            向wxpay post的请求数据
     * @param connectTimeoutMs
     *            连接超时时间，单位是毫秒
     * @param readTimeoutMs
     *            读超时时间，单位是毫秒
     * @return API返回数据
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws TransformerException 
     * @throws Exception 
     */
    public Map<String, String> authCodeToOpenid(Map<String, String> reqData, int connectTimeoutMs,
                                                int readTimeoutMs)
        throws SAXException,
        IOException,
        ParserConfigurationException,
        InvalidKeyException,
        NoSuchAlgorithmException,
        TransformerException {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_AUTHCODETOOPENID_URL;
        }
        else {
            url = WXPayConstants.AUTHCODETOOPENID_URL;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData),
            connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }
    /**
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param params
     * @return   Map<String, String>
     * @throws SAXException 
     * @throws IOException 
     * @throws TransformerException 
     * @throws ParserConfigurationException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @see 
     */
    public Map<String, String> promotion(Map<String, String> params)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        ParserConfigurationException,
        TransformerException,
        IOException,
        SAXException {
        return this.promotion(params, config.getHttpConnectTimeoutMs(),
            config.getHttpReadTimeoutMs());
    }

    /**
     * Description: <br> 1、…<br> 2、…<br> Implement: <br> 1、…<br> 2、…<br>
     * 
     * @return Map<String, String>
     * @throws IOException 
     * @throws TransformerException 
     * @throws ParserConfigurationException 
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws SAXException 
     * @see
     */
    public Map<String, String> promotion(Map<String, String> reqData, int connectTimeoutMs,
                                         int readTimeoutMs)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        ParserConfigurationException,
        TransformerException,
        IOException,
        SAXException {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_PROMOTION_URL;
        }
        else {
            url = WXPayConstants.PROMOTION_URL;
        }
        reqData.put("mch_appid", config.getMchAppid());
        reqData.put("check_name", IsCheckName.CHECK.getCode());
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData),
            connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }

  
}
