package com.zzd.spring.alipay.config.wechat;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.ValidationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.zzd.spring.common.utils.WXPayConstants;
import com.zzd.spring.common.utils.WXPayConstants.SignType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * 〈微信工具类〉 〈功能详细描述〉
 * 
 * @see WXUtil
 * @since
 */
public class WXUtil implements Serializable {
    /** 日志 */
    private static final Logger LOG = LoggerFactory.getLogger(WXUtil.class);

    private static final long serialVersionUID = 1L;

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML
     *            XML字符串
     * @return XML数据转换后的Map
     * @throws IOException 
     * @throws SAXException 
     * @throws ParserConfigurationException 
     * @throws Exception 
     */
    public static Map<String, String> xmlToMap(String strXML)
        throws SAXException,
        IOException,
        ParserConfigurationException {
        Map<String, String> data = new HashMap<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        InputStream stream = new ByteArrayInputStream(strXML.getBytes(WXPayConstants.CHARSET));
        org.w3c.dom.Document doc = documentBuilder.parse(stream);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        for (int idx = 0; idx < nodeList.getLength(); ++idx) {
            Node node = nodeList.item(idx);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element)node;
                data.put(element.getNodeName(), element.getTextContent());
            }
        }
        try {
            stream.close();
        }
        catch (Exception ex) {
            LOG.error("连接关闭出现异常 :{}", ex.getMessage());
        }
        return data;
    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data
     *            Map类型数据
     * @return XML格式的字符串
     * @throws ParserConfigurationException 
     * @throws TransformerException 
     * @throws Exception 
     */
    public static String mapToXml(Map<String, String> data)
        throws ParserConfigurationException,
        TransformerException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.newDocument();
        Element root = document.createElement("xml");
        document.appendChild(root);
        data.forEach((key, value) -> {
            if (value == null) {
                value = "";
            }
            value = value.trim();
            Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        });
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, WXPayConstants.CHARSET);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString();
        try {
            writer.close();
        }
        catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return output;
    }

    /**
     * 生成带有 sign 的 XML 格式字符串
     *
     * @param data
     *            Map类型数据
     * @param key
     *            API密钥
     * @return 含有sign字段的XML
     * @throws TransformerException 
     * @throws ParserConfigurationException 
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     */
    public static String generateSignedXml(final Map<String, String> data, String key)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        UnsupportedEncodingException,
        ParserConfigurationException,
        TransformerException {
        return generateSignedXml(data, key, SignType.MD5);
    }

    /**
     * 生成带有 sign 的 XML 格式字符串
     *
     * @param data
     *            Map类型数据
     * @param key
     *            API密钥
     * @param signType
     *            签名类型
     * @return 含有sign字段的XML
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws TransformerException 
     * @throws ParserConfigurationException 
     */
    public static String generateSignedXml(final Map<String, String> data, String key,
                                           SignType signType)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        UnsupportedEncodingException,
        ParserConfigurationException,
        TransformerException {
        String sign = generateSignature(data, key, signType);
        data.put(WXPayConstants.FIELD_SIGN, sign);
        return mapToXml(data);
    }

    /**
     * 判断签名是否正确
     *
     * @param xmlStr
     *            XML格式数据
     * @param key
     *            API密钥
     * @return 签名是否正确
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws Exception 
     */
    public static boolean isSignatureValid(String xmlStr, String key)
        throws SAXException,
        IOException,
        ParserConfigurationException,
        InvalidKeyException,
        NoSuchAlgorithmException {
        Map<String, String> data = xmlToMap(xmlStr);
        if (!data.containsKey(WXPayConstants.FIELD_SIGN)) {
            return false;
        }
        String sign = data.get(WXPayConstants.FIELD_SIGN);
        return generateSignature(data, key).equals(sign);
    }

    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。使用MD5签名。
     *
     * @param data
     *            Map类型数据
     * @param key
     *            API密钥
     * @return 签名是否正确
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, String> data, String key)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        UnsupportedEncodingException {
        return isSignatureValid(data, key, SignType.MD5);
    }

    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。
     *
     * @param data
     *            Map类型数据
     * @param key
     *            API密钥
     * @param signType
     *            签名方式
     * @return 签名是否正确
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, String> data, String key, SignType signType)
        throws InvalidKeyException, 
        NoSuchAlgorithmException, 
        UnsupportedEncodingException {
        if (!data.containsKey(WXPayConstants.FIELD_SIGN)) {
            return false;
        }
        String sign = data.get(WXPayConstants.FIELD_SIGN);
        return generateSignature(data, key, signType).equals(sign);
    }

    /**
     * 生成签名
     *
     * @param data
     *            待签名数据
     * @param key
     *            API密钥
     * @return 签名
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     */
    public static String generateSignature(final Map<String, String> data, String key)
        throws InvalidKeyException,
        NoSuchAlgorithmException,
        UnsupportedEncodingException {
        return generateSignature(data, key, SignType.MD5);
    }

    /**
     * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
     *
     * @param data
     *            待签名数据
     * @param key
     *            API密钥
     * @param signType
     *            签名方式
     * @return 签名
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException  
     */
    public static String generateSignature(final Map<String, String> data, String key,
                                           SignType signType)
        throws NoSuchAlgorithmException,
        UnsupportedEncodingException,
        InvalidKeyException {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals(WXPayConstants.FIELD_SIGN)) {
                continue;
            }
            if (data.get(k).trim().length() > 0) { // 参数值为空，则不参与签名
                sb.append(k).append("=").append(data.get(k).trim()).append("&");
            }
        }
        sb.append("key=").append(key);
        if (SignType.MD5.equals(signType)) {
            return doMD5(sb.toString()).toUpperCase();
        }
        else if (SignType.HMACSHA256.equals(signType)) {
            return doHMACSHA256(sb.toString(), key);
        }
        else {
            throw new ValidationException(String.format("Invalid sign_type: %s", signType));
        }
    }

    /**
     * 获取随机字符串 Nonce Str
     *
     * @return String 随机字符串
     */
    public static String generateNonceStr() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }

    /**
     * 生成 MD5
     *
     * @param data
     *            待处理数据
     * @return MD5结果
     * @throws NoSuchAlgorithmException 
     * @throws UnsupportedEncodingException 
     */
    public static String doMD5(String data)
        throws NoSuchAlgorithmException,
        UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance(WXPayConstants.MD5);
        byte[] array = md.digest(data.getBytes(WXPayConstants.CHARSET));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 生成 HMACSHA256
     * 
     * @param data
     *            待处理数据
     * @param key
     *            密钥
     * @return 加密结果
     * @throws NoSuchAlgorithmException 
     * @throws UnsupportedEncodingException 
     * @throws InvalidKeyException 
     * @throws Exception 
     */
    public static String doHMACSHA256(String data, String key)
        throws NoSuchAlgorithmException,
        UnsupportedEncodingException,
        InvalidKeyException {
        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(WXPayConstants.CHARSET),
            "HmacSHA256");
        sha256HMAC.init(secretKey);
        byte[] array = sha256HMAC.doFinal(data.getBytes(WXPayConstants.CHARSET));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }
}
