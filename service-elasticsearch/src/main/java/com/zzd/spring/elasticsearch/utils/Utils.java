package com.zzd.spring.elasticsearch.utils;


import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;


/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangzhende
 * @version 2018年11月7日
 * @see Utils
 * @since
 */
public class Utils {
    // public static void main(String[] args) {
    // String str1 = "AA_ZAA".toLowerCase();// 带下划线的字符串
    // StringBuffer sbf = new StringBuffer(str1);// 首先先转换成小写
    // System.out.println(sbf.toString());
    // StringBuffer sb = camel(sbf);
    // System.out.println(sb);
    // }

    /**
     * 下划线转驼峰
     * 
     * @param str
     * @return StringBuffer
     */
    public static StringBuffer camel(StringBuffer str) {
        // 利用正则删除下划线，把下划线后一位改成大写
        Pattern pattern = Pattern.compile("_(\\w)");
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if (matcher.find()) {
            sb = new StringBuffer();
            // 将当前匹配子串替换为指定字符串，并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个StringBuffer对象里。
            // 正则之前的字符和被替换的字符
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
            // 把之后的也添加到StringBuffer对象里
            matcher.appendTail(sb);
        }
        else {
            return sb;
        }
        return camel(sb);
    }

    /**
     * 驼峰转下划线
     * 
     * @param str
     * @return StringBuffer
     */
    public static StringBuffer underline(StringBuffer str) {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if (matcher.find()) {
            sb = new StringBuffer();
            // 将当前匹配子串替换为指定字符串，并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个StringBuffer对象里。
            // 正则之前的字符和被替换的字符
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
            // 把之后的也添加到StringBuffer对象里
            matcher.appendTail(sb);
        }
        else {
            return sb;
        }
        return underline(sb);
    }

    /**
     * 获取前一天
     * 
     * @param date
     * @return Date
     * @see
     */
    public static Date getYesterday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * 获取0点
     * 
     * @param date
     * @return Date
     * @see
     */
    public static Date getHeadTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * Object 转Map
     * 
     * @param obj
     * @return Map<String,Object>
     * @see
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseToMap(Object obj) {
        //即使是null也要保留，否贼会被舍弃，SerializerFeature.WriteMapNullValue
        Map<String, Object> map = JSONObject.parseObject(
            JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue), Map.class);
        return map;
    }

    /**
     * get 23:59:59
     * 
     * @param date
     * @return Date
     * @see
     */
    public static Date getTailTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
}
