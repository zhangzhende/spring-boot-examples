package com.zzd.spring.alipay.servcie;


import java.util.Map;

import com.zzd.spring.alipay.config.wechat.WXPay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;


/**
 * 〈微信支付工具类〉 〈功能详细描述〉
 * 
 * @since
 */
@Component
public class WeChatPayService {
    /** 日志 */
    private static final Logger LOG = LoggerFactory.getLogger(WeChatPayService.class);

    /** 常量 */
    private static final String SUCCESS = "SUCCESS";

    /** 常量 */
    private static final String FAIL = "FAIL";

    /** 微信支付核心类 */
    @Autowired
    private WXPay wxPay;

    /**
     * Description: 创建订单: 必传参数列表 商品描述 body String(128)商品简单描述 商户订单号 out_trade_no
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一 标价金额 total_fee 订单总金额，单位为分 终端IP
     * spbill_create_ip APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。 交易类型 参照WXPayType.java
     * String(16)取code传 <br> 1、…<br> 2、…<br> Implement: <br> 1、…<br> 2、…<br>
     * 
     * @param params
     * @return 订单信息,微信订单号等
     * @see
     */
    public Map<String, String> createOrder(Map<String, String> params) {
        Map<String, String> result = null;
        try {
            result = wxPay.unifiedOrder(params);
            LOG.info("订单创建{},订单信息{},返回信息{}",
                SUCCESS.equals(result.get("return_code")) ? SUCCESS : FAIL,
                JSON.toJSONString(params), JSON.toJSONString(result));
        }
        catch (Exception e) {
            LOG.error("微信支付失败,订单信息:{},异常信息", JSON.toJSONString(params), e);
        }

        return result;
    }

    /**
     * Description:查询订单 必传参数列表: 微信订单号(transaction_id)和商户订单号(out_trade_no)二选一 <br> 1、…<br> 2、…<br>
     * Implement: <br> 1、…<br> 2、…<br>
     * 
     * @param params
     * @return 订单信息
     * @see
     */
    public Map<String, String> queryOrder(Map<String, String> params) {
        Map<String, String> result = null;
        try {
            result = wxPay.orderQuery(params);
        }
        catch (Exception e) {
            LOG.error("微信订单查询失败,请求信息:{},异常信息{}", JSON.toJSONString(params), e);
        }
        return result;
    }

    /**
     * Description: 关闭订单 必传参数列表: 商户订单号(out_trade_no)<br> 1、…<br> 2、…<br> Implement: <br> 1、…<br>
     * 2、…<br>
     * 
     * @param params
     * @return 订单信息,执行结果
     * @see
     */
    public Map<String, String> closeOrder(Map<String, String> params) {
        Map<String, String> result = null;
        try {
            result = wxPay.closeOrder(params);
            LOG.info("订单关闭{},订单信息{},返回信息{}",
                SUCCESS.equals(result.get("return_code")) ? SUCCESS : FAIL,
                JSON.toJSONString(params), JSON.toJSONString(result));
        }
        catch (Exception e) {
            LOG.error("微信订单关闭失败,请求信息:{},异常信息{}", JSON.toJSONString(params), e);
        }
        return result;
    }

    /**
     * Description: 申请退款 必传参数列表: 微信订单号(transaction_id)和商户订单号(out_trade_no)二选一
     * 商户退款单号(out_refund_no): 商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
     * 订单金额(total_fee): 订单总金额，单位为分，只能为整数 退款金额(refund_fee): 退款总金额，订单总金额，单位为分，只能为整数
     * 
     * @param params
     * @return 退款订单信息,退款结果
     * @see
     */
    public Map<String, String> refundOrder(Map<String, String> params) {
        Map<String, String> result = null;
        try {
            result = wxPay.refund(params);
            LOG.info("订单申请退款{},订单信息{},返回信息{}",
                SUCCESS.equals(result.get("return_code")) ? SUCCESS : FAIL,
                JSON.toJSONString(params), JSON.toJSONString(result));
        }
        catch (Exception e) {
            LOG.error("订单申请退款失败,请求信息:{},异常信息{}", JSON.toJSONString(params), e);
        }
        return result;
    }

    /**
     * Description: 退款查询 必传参数列表:
     * 微信订单号(transaction_id)、商户订单号(out_trade_no)、商户退款单号(out_refund_no)、商户退款单号(refund_id)四选一选一
     * 
     * @param params
     * @return 订单状态
     * @see
     */
    public Map<String, String> refundQuery(Map<String, String> params) {
        Map<String, String> result = null;
        try {
            result = wxPay.refundQuery(params);
        }
        catch (Exception e) {
            LOG.error("退款查询失败,请求信息:{},异常信息{}", JSON.toJSONString(params), e);
        }
        return result;
    }

    /**
     * Description: <br> 1、…<br> 2、…<br> Implement: <br> 1、…<br> 2、…<br>
     * 
     * @param params
     * @return Map<String, String>
     * @see
     */
    public Map<String, String> promotion(Map<String, String> params) {
        Map<String, String> result = null;
        try {
            result = wxPay.promotion(params);
        }
        catch (Exception e) {
            LOG.error("付款到零钱失败,请求信息:{},异常信息{}", JSON.toJSONString(params), e);
        }
        return result;
    }
}
