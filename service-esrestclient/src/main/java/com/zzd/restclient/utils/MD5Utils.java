

package com.zzd.restclient.utils;


import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangzhende
 * @version 2018年11月9日
 * @see MD5Utils
 * @since
 */

public class MD5Utils {
    /**
     * 
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MD5Utils.class);

    /**
     * MD5方法
     * 
     * @param sysName
     *            明文
     * @param timestamp
     *            密钥
     * @return 密文
     * @throws Exception
     *             s
     */
    public static String md5(String sysName, String timestamp)
        throws Exception {
        // 加密后的字符串
        String encodeStr = DigestUtils.md5Hex(sysName + timestamp);
//        System.out.println("MD5加密后的字符串为:encodeStr=" + encodeStr);
        return encodeStr;
    }

    /**
     * MD5验证方法
     * 
     * @param sysName
     *            明文
     * @param timestamp
     *            密钥
     * @param md5
     *            密文
     * @return true/false
     * @throws Exception
     *             s
     */
    public static boolean verify(String sysName, String timestamp, String md5)
        throws Exception {
        // 根据传入的密钥进行验证
        String md5Text = md5(sysName, timestamp);
        if (md5Text.equalsIgnoreCase(md5)) {
            System.out.println("MD5验证通过");
            return true;
        }
        return false;
    }

    /**
     * 验证
     * 
     * @param sysName
     * @param timestamp
     * @param md5
     * @param timeDifference
     * @return boolean
     * @throws Exception
     *             e
     * @see
     */
    public static boolean check(String sysName, long timestamp, String md5, long timeDifference) {
        Date date = new Date();
        long time = date.getTime();
        if (Math.abs(time - timestamp) > timeDifference) {
            LOGGER.info("sysName:{},timestamp:{},md5:{},验证不通过----超时", sysName, timestamp, md5);
            return false;
        }
        else {
            try {
                if (verify(sysName, String.valueOf(timestamp), md5)) {
                    return true;
                }
                else {
                    LOGGER.info("sysName:{},timestamp:{},md5:{},验证不通过----秘钥不正确", sysName,
                        timestamp, md5);
                    return false;
                }
            }
            catch (Exception e) {
                LOGGER.info("sysName:{},timestamp:{},md5:{},验证异常----异常", sysName, timestamp, md5);
                e.printStackTrace();
                return false;
            }
        }
    }
}
