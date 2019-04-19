package com.zzd.spring.alipay;


import com.zzd.spring.alipay.servcie.AliPayService;
import com.zzd.spring.alipay.utils.SnUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradeCreateModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;


/**
 * 〈支付宝支付单元测试〉 〈功能详细描述〉
 * 
 * @see AliPayTest
 * @since
 */
@SpringBootTest(classes = ServiceAlipayApplication.class)
@RunWith(SpringRunner.class)
public class AliPayTest {
    /** 支付宝支付工具 */
    @Autowired
    private AliPayService aliPay;

    /**
     * 订单创建 Description: <br> 1、…<br> 2、…<br> Implement: <br> 1、…<br> 2、…<br>
     * 
     * @see
     */
    @Test
    public void createTest() {
        AlipayTradeCreateModel model = new AlipayTradeCreateModel();
        model.setSubject("宫保鸡丁");
        model.setTotalAmount("50");
        model.setTimeoutExpress("5m");
        model.setBody(
            "据报道,日本第一大运营商NTT DoCoMo举办2018年夏季新品发布会,华为三摄旗舰P20 Pro借机正式进入日本市场,将于6月下旬在NTT DoCoMo渠道全面开售。 与... ");
        model.setOutTradeNo(SnUtils.generateOrderNo());
        model.setBuyerLogonId("ukbxwc8980@sandbox.com");
        try {
            AlipayTradeCreateResponse response = aliPay.create(model);
            System.out.println(JSON.toJSON(response));
        }
        catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 支付 Description: <br> 1、…<br> 2、…<br> Implement: <br> 1、…<br> 2、…<br>
     * 
     * @see
     */
    @Test
    public void payTest() {
        AlipayTradePayModel model = new AlipayTradePayModel();
        model.setTotalAmount("50");
        model.setOutTradeNo(SnUtils.generateOrderNo());
        model.setSubject("话费充值50元");
        model.setScene("bar_code");
        model.setAuthCode("281916218172491603");
        try {
            aliPay.pay(model);
            System.out.println(JSON.toJSONString(model));
        }
        catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询订单 Description: <br> 1、…<br> 2、…<br> Implement: <br> 1、…<br> 2、…<br>
     * 
     * @see
     */
    @Test
    public void queryTest() {
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo("08322018061413513246758639319752");
        // model.setTradeNo("2014072300007148");
        try {
            AlipayTradeQueryResponse tradeQuery = aliPay.tradeQuery(model);
            System.out.println(JSON.toJSONString(tradeQuery));
        }
        catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭订单测试
     */
    @Test
    public void closeTest() {
        AlipayTradeCloseModel model = new AlipayTradeCloseModel();
        // model.setTradeNo("2018051821001004870200239298");// 支付宝订单号
        model.setOutTradeNo("25732018061415120129164379887589");// 商家订单号
        try {
            AlipayTradeCloseResponse close = aliPay.close(model);
            System.out.println(JSON.toJSONString(close));
        }
        catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 退款 Description: <br> 1、…<br> 2、…<br> Implement: <br> 1、…<br> 2、…<br>
     * 
     * @see
     */
    @Test
    public void refundTest() {
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        // model.setTradeNo("2018051821001004870200239295");// 支付宝订单号
        model.setOutTradeNo("46432018061415575563985293261570");// 商家订单号
        model.setRefundAmount("5");
        model.setOutRequestNo(SnUtils.generateOrderNo());
        try {
            AlipayTradeRefundResponse refund = aliPay.refund(model);
            System.out.println(JSON.toJSONString(refund));
        }
        catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * app支付测试 Description: <br> 1、…<br> 2、…<br> Implement: <br> 1、…<br> 2、…<br>
     * 
     * @see
     */
    @Test
    public void appPayTest() {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        // model.setTradeNo("2018051821001004870200239295");// 支付宝订单号
        // model.setOutTradeNo("46432018061415575563985293261570");// 商家订单号
        // model.setRefundAmount("5");
        // model.setOutRequestNo(SnUtils.generateOrderNo());
        model.setOutTradeNo(SnUtils.generateOrderNo());
        model.setSubject("中国电信话费充值");
        model.setTotalAmount("50");
        model.setProductCode("QUICK_MSECURITY_PAY");
        model.setBody(
            "据报道,日本第一大运营商NTT DoCoMo举办2018年夏季新品发布会,华为三摄旗舰P20 Pro借机正式进入日本市场,将于6月下旬在NTT DoCoMo渠道全面开售。 与... ");
        model.setOutTradeNo(SnUtils.generateOrderNo());
        try {
            AlipayTradeAppPayResponse refund = aliPay.appPay(model);
            System.out.println(JSON.toJSONString(refund));
        }
        catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 统一收单线下交易预创建,扫一扫支付
     * 
     * @see
     */
    @Test
    public void precreate() {
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setSubject("茶易余额充值");
        model.setTotalAmount("5000");
        model.setOutTradeNo(SnUtils.generateOrderNo());
        try {
            AlipayTradePrecreateResponse refund = aliPay.tradePrecreate(model);
            System.out.println(JSON.toJSONString(refund));
            System.out.println(refund.getQrCode());// 二维码内容,需要二维码生成工具生成
        }
        catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * web支付
     * 
     * @see
     */
    @Test
    public void webPayTest() {
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setSubject("茶易余额充值");
        model.setTotalAmount("5000");
        model.setOutTradeNo(SnUtils.generateOrderNo());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("retUrl", "http://www.baidu.com");
        model.setPassbackParams(jsonObject.toJSONString());
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        try {
            AlipayTradePagePayResponse response = aliPay.webPay(model);
            System.out.println(response.getBody());// url
        }
        catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 转账
     * 
     * @see
     */
    @Test
    public void transferAccountsTest() {
        AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();
        model.setAmount("1000000");
        model.setOutBizNo(SnUtils.generateOrderNo());
        model.setPayeeAccount("ukbxwc8980@sandbox.com");
        model.setPayeeType("ALIPAY_LOGONID");
        model.setRemark("中奖了");

        try {
            AlipayFundTransToaccountTransferResponse response = aliPay.transferAccounts(model);
            System.out.println(JSON.toJSONString(response));
        }
        catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * wap支付
     * 
     * @see
     */
    @Test
    public void wapPayTest() {
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setTotalAmount("100");
        model.setOutTradeNo(SnUtils.generateOrderNo());
        model.setSubject("茶易通余额充值");
        try {
            AlipayTradeWapPayResponse response = aliPay.wapPay(model);
            System.out.println(JSON.toJSONString(response));
            System.out.println(response.getBody());
        }
        catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }
}
