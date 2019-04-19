package com.zzd.spring.common.utils;

/**
 * 常量 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author liyonggang
 * @version 2018年6月7日
 * @see WXPayConstants
 * @since
 */
public class WXPayConstants {

    public enum SignType {
        /** 加密方式MD5 */
        MD5,
        /** 加密方式HMACSHA256 */
        HMACSHA256
    }

    /** 失败 */
    public static final String FAIL = "FAIL";

    /** 成功 */
    public static final String SUCCESS = "SUCCESS";

    /** 字符集 */
    public static final String CHARSET = "UTF-8";

    /** 加密方式HMAC-SHA256 */
    public static final String HMACSHA256 = "HMAC-SHA256";

    /** 加密方式MD5 */
    public static final String MD5 = "MD5";

    /** 签名 */
    public static final String FIELD_SIGN = "sign";

    /** 签名类型 */
    public static final String FIELD_SIGN_TYPE = "sign_type";

    /** 刷卡支付 */
    public static final String MICROPAY_URL = "https://api.mch.weixin.qq.com/pay/micropay";

    /** 下单 */
    public static final String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /** 订单查询 */
    public static final String ORDERQUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

    /** 反向支付 */
    public static final String REVERSE_URL = "https://api.mch.weixin.qq.com/secapi/pay/reverse";

    /** 关闭订单 */
    public static final String CLOSEORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";

    /** 退款 */
    public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    /** 退款查询 */
    public static final String REFUNDQUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";

    /** 账单下载 */
    public static final String DOWNLOADBILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";

    /** 账单 */
    public static final String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";

    /** 短链接转换 */
    public static final String SHORTURL_URL = "https://api.mch.weixin.qq.com/tools/shorturl";

    /** 认证 */
    public static final String AUTHCODETOOPENID_URL = "https://api.mch.weixin.qq.com/tools/authcodetoopenid";

    /** 企业付款到微信 */
    public static final String PROMOTION_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    // sandbox
    /** 测试环境刷卡支付 */
    public static final String SANDBOX_MICROPAY_URL = "https://api.mch.weixin.qq.com/sandboxnew/pay/micropay";

    /** 测试环境下单 */
    public static final String SANDBOX_UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/sandboxnew/pay/unifiedorder";

    /** 测试环境订单查询 */
    public static final String SANDBOX_ORDERQUERY_URL = "https://api.mch.weixin.qq.com/sandboxnew/pay/orderquery";

    /** 测试环境反向支付 */
    public static final String SANDBOX_REVERSE_URL = "https://api.mch.weixin.qq.com/sandboxnew/secapi/pay/reverse";

    /** 测试环境关闭订单 */
    public static final String SANDBOX_CLOSEORDER_URL = "https://api.mch.weixin.qq.com/sandboxnew/pay/closeorder";

    /** 测试环境退款 */
    public static final String SANDBOX_REFUND_URL = "https://api.mch.weixin.qq.com/sandboxnew/secapi/pay/refund";

    /** 测试环境退款查询 */
    public static final String SANDBOX_REFUNDQUERY_URL = "https://api.mch.weixin.qq.com/sandboxnew/pay/refundquery";

    /** 测试环境对账单下载 */
    public static final String SANDBOX_DOWNLOADBILL_URL = "https://api.mch.weixin.qq.com/sandboxnew/pay/downloadbill";

    /** 测试环境导出账单 */
    public static final String SANDBOX_REPORT_URL = "https://api.mch.weixin.qq.com/sandboxnew/payitil/report";

    /** 测试环境短链接转换 */
    public static final String SANDBOX_SHORTURL_URL = "https://api.mch.weixin.qq.com/sandboxnew/tools/shorturl";

    /** 测试环境认证 */
    public static final String SANDBOX_AUTHCODETOOPENID_URL = "https://api.mch.weixin.qq.com/sandboxnew/tools/authcodetoopenid";

    /** 测试环境企业付款到微信 */
    public static final String SANDBOX_PROMOTION_URL = "https://api.mch.weixin.qq.com/sandboxnew/mmpaymkttransfers/promotion/transfers";

    public enum IsCheckName {
        /** FORCE_CHECK */
        CHECK("FORCE_CHECK", "强校验真实姓名"),
        /** NO_CHECK */
        NOCHECK("NO_CHECK", "不校验真实姓名 ");
        /**
         * code
         */
        private String code;

        /**
         * message
         */
        private String message;

        IsCheckName(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

    }
}