
/**
 * @标题: WXPayTest.java @包名： com.newlixon.common.pay.wechat.test
 * @功能描述：TODO @author： liyonggang @创建时间： 2018年5月21日 上午10:47:09
 * @version v1.0
 */

package com.zzd.spring.alipay;


import java.util.Map;

import com.zzd.spring.alipay.servcie.WeChatPayService;
import com.zzd.spring.alipay.utils.SnUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;


/**
 * @类描述
 * @创建时间 2018年5月21日上午10:47:09
 * @author liyonggang
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class WXPayTest {
    /** 微信支付工具 */
    @Autowired
    private WeChatPayService payUtil;

    /**
     * 创建订单测试
     */
    @Test
    public void createOrderTest() {
        Map<String, String> params = Maps.newHashMap();
        params.put("body", "腾讯充值中心-QQ会员充值");
        params.put("out_trade_no", SnUtils.generateOrderNo());
        params.put("total_fee", "1010");
        params.put("spbill_create_ip", "123.12.12.123");
        params.put("notify_url", "www.baodu.com");
        params.put("trade_type", "APP");
        Map<String, String> unifiedOrder = null;
        try {
            unifiedOrder = payUtil.createOrder(params);
            System.out.println(JSON.toJSONString(unifiedOrder));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 订单查询
     */
    @Test
    public void queryOrderTest() {
        Map<String, String> params = Maps.newHashMap();
        // params.put("transaction_id", SnUtils.generateOrderNo());
        params.put("out_trade_no", "25485412378545478567856");
        Map<String, String> unifiedOrder = null;
        try {
            unifiedOrder = payUtil.queryOrder(params);
            System.out.println(JSON.toJSONString(unifiedOrder));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭订单
     */
    @Test
    public void closeOrderTest() {
        Map<String, String> params = Maps.newHashMap();
        params.put("out_trade_no", "25485412378545478567856");
        Map<String, String> unifiedOrder = null;
        try {
            unifiedOrder = payUtil.closeOrder(params);
            System.out.println(JSON.toJSONString(unifiedOrder));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 申请退款
     */
    @Test
    public void refundOrderTest() {
        Map<String, String> params = Maps.newHashMap();
        params.put("out_trade_no", "25485412378545478567856");
        // params.put("transaction_id", "25485412378545478567856");
        params.put("out_refund_no", SnUtils.generateOrderNo());
        params.put("total_fee", "15000");
        params.put("refund_fee", "15000");
        Map<String, String> unifiedOrder = null;
        try {
            unifiedOrder = payUtil.refundOrder(params);
            System.out.println(JSON.toJSONString(unifiedOrder));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 退款信息查询
     */
    @Test
    public void refundQueryTest() {
        Map<String, String> params = Maps.newHashMap();
        params.put("out_trade_no", "25485412378545478567856");
        // params.put("transaction_id", "25485412378545478567856");
        // params.put("out_refund_no", SnUtils.generateOrderNo());
        // params.put("refund_id", SnUtils.generateOrderNo());
        Map<String, String> unifiedOrder = null;
        try {
            unifiedOrder = payUtil.refundQuery(params);
            System.out.println(JSON.toJSONString(unifiedOrder));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
