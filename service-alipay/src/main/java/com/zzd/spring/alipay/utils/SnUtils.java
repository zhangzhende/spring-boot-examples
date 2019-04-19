package com.zzd.spring.alipay.utils;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * 订单号生成方案
 * 
 * @see SnUtils
 * @since
 */
public class SnUtils implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 默认DEFAULT_LENGTH
     */
    private static final int DEFAULT_LENGTH = 32;

    /**
     * 默认PREFIX_LENGTH
     */
    private static final int PREFIX_LENGTH = 4;

    /**
     * 日期格式化
     */
    @SuppressWarnings("all")
    private static final DateFormat DF = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 生成 唯一 订单号
     *
     * @param prefix
     * @return String
     */
    public static String generateOrderNo() {
        return generateOrderNo("1234", DEFAULT_LENGTH);
    }

    /**
     * @return String
     */
    public static String generateOrderNo(String prefix) {
        return generateOrderNo(prefix, DEFAULT_LENGTH);
    }

    /**
     * @return String
     */
    public static String generateOrderNo(String prefix, int length) {
        if (StringUtils.isBlank(prefix) || prefix.length() < PREFIX_LENGTH
            || prefix.length() > length || length < DEFAULT_LENGTH) {
            throw new IllegalArgumentException("Prefix Illegal");
        }

        StringBuilder sb = new StringBuilder();

        if (prefix.length() > PREFIX_LENGTH) {
            sb.append(prefix.substring(0, PREFIX_LENGTH));
        }

        sb.append(RandomStringUtils.randomNumeric(PREFIX_LENGTH));
        sb.append(DF.format(new Date()));
        sb.append(RandomStringUtils.randomNumeric(length - sb.length()));

        return sb.toString();
    }
}
