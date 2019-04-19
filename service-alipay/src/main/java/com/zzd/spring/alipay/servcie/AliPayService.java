package com.zzd.spring.alipay.servcie;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.zzd.spring.alipay.config.ali.AlipayConfig;
import com.zzd.spring.alipay.config.ali.InitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;

/**
 * Created by Administrator on 2019/4/8 0008.
 */
@Service
public class AliPayService {
    /**
     * SUCCESS
     */
    private static final String SUCCESS = "成功";

    /**
     * FAIL
     */
    private static final String FAIL = "失败";
    private static final Logger LOG = LoggerFactory.getLogger(AliPayService.class);
    @Autowired
    private InitUtil initUtil;
    @Autowired
    private AlipayConfig alipayConfig;



    /**
     * Description: <br> 1、…<br> 2、…<br> Implement: <br> 1、…<br> 2、…<br>
     *
     * @描述:创建支付宝订单,商户通过该接口进行交易的创建下单 必传参数: out_trade_no:商户订单号,
     *                              total_amount:订单金额,buyer_id:买家的支付宝唯一用户号（2088开头的16位纯数字）,和buyer_logon_id(买家支付宝帐号)不能同时为空subject:订单标题
     * @param model
     * @return AlipayTradeCreateResponse
     * @throws AlipayApiException
     * @see
     */
    public AlipayTradeCreateResponse create(AlipayTradeCreateModel model)
            throws AlipayApiException {
        // 实例化客户端
        AlipayClient alipayClient = initUtil.newAliClient();
        AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
        request.setNotifyUrl(alipayConfig.getNotifyUrl() + alipayConfig.getPayBackUrl());// 支付结果回调地址
        request.setBizModel(model);
        AlipayTradeCreateResponse response = alipayClient.execute(request);
        LOG.info("订单创建{}: 订单信息:{},返回信息:{}", response.isSuccess() ? SUCCESS : FAIL,
                JSON.toJSONString(model), JSON.toJSONString(response));
        return response;
    }

    /**
     * @描述:关闭订单,用于交易创建后，用户在一定时间内未进行支付，可调用该接口直接将未付款的交易进行关闭。 必传参数:trade_no(该交易在支付宝系统中的交易流水号)和out_trade_no(订单支付时传入的商户订单号)两个不能同时为空,如果同时存在优先取trade_no
     * @return AlipayTradeCloseResponse
     * @throws AlipayApiException
     */
    public AlipayTradeCloseResponse close(AlipayTradeCloseModel model)
            throws AlipayApiException {// 实例化客户端
        AlipayClient alipayClient = initUtil.newAliClient();
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        request.setBizModel(model);
        AlipayTradeCloseResponse response = alipayClient.execute(request);
        LOG.info("订单关闭{}: 订单信息:{},返回信息:{}", response.isSuccess() ? SUCCESS : FAIL,
                JSON.toJSONString(model), JSON.toJSONString(response));
        return response;
    }

    /**
     * @描述:撤销订单,支付交易返回失败或支付系统超时，调用该接口撤销交易。 如果此订单用户支付失败，支付宝系统会将此订单关闭；如果用户支付成功， 支付宝系统会将此订单资金退还给用户。
     * 注意：只有发生支付系统超时或者支付结果未知时可调用撤销，其他正常支付的单如需实现相同功能请调用申请退款API。
     * 提交支付交易后调用【查询订单API】，没有明确的支付结果再调用【撤销订单API】。 必传参数
     * out_trade_no(原支付请求的商户订单号)和trade_no(支付宝交易号)不能同时为空
     * @return AlipayTradeCancelResponse
     * @throws AlipayApiException
     */
    public AlipayTradeCancelResponse cancel(AlipayTradeCancelModel model)
            throws AlipayApiException {
        // 实例化客户端
        AlipayClient alipayClient = initUtil.newAliClient();
        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
        request.setBizModel(model);
        AlipayTradeCancelResponse response = alipayClient.execute(request);
        LOG.info("订单撤销{}: 订单信息:{},返回信息:{}", response.isSuccess() ? SUCCESS : FAIL,
                JSON.toJSONString(model), JSON.toJSONString(response));
        return response;
    }

    /**
     * 读取用户手机支付宝“付款码”/声波获取设备（如麦克风）读取用户手机支付宝的声波信息后，将二维码或条码信息/声波信息通过本接口上送至支付宝发起支付。 必传参数
     * @return AlipayTradePayResponse
     * @throws AlipayApiException
     */
    public AlipayTradePayResponse pay(AlipayTradePayModel model)
            throws AlipayApiException {
        // 实例化客户端
        AlipayClient alipayClient = initUtil.newAliClient();
        AlipayTradePayRequest request = new AlipayTradePayRequest();
        request.setNotifyUrl(alipayConfig.getNotifyUrl() + alipayConfig.getPayBackUrl());// 支付结果回调地址
        request.setBizModel(model);
        AlipayTradePayResponse response = alipayClient.execute(request);
        LOG.info("订单付款{}: 订单信息:{},返回信息:{}", response.isSuccess() ? SUCCESS : FAIL,
                JSON.toJSONString(model), JSON.toJSONString(response));
        return response;
    }

    /**
     * 该接口提供所有支付宝支付订单的查询，商户可以通过该接口主动查询订单状态 out_trade_no(原支付请求的商户订单号)和trade_no(支付宝交易号)不能同时为空
     * @return AlipayTradeQueryResponse
     * @throws AlipayApiException
     */
    public AlipayTradeQueryResponse tradeQuery(AlipayTradeQueryModel model)
            throws AlipayApiException {
        // 实例化客户端
        AlipayClient alipayClient = initUtil.newAliClient();
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizModel(model);
        return alipayClient.execute(request);
    }

    /**
     * 退款信息查询,商户可使用该接口查询自已通过alipay.trade.refund提交的退款请求是否执行成功。
     * 该接口的返回码10000，仅代表本次查询操作成功，不代表退款成功。如果该接口返回了查询数据，则代表退款成功，
     * 如果没有查询到则代表未退款成功，可以调用退款接口进行重试。重试时请务必保证退款请求号一致。
     * @return AlipayTradeFastpayRefundQueryResponse
     * @throws AlipayApiException
     */
    public AlipayTradeFastpayRefundQueryResponse refundQuery(AlipayTradeFastpayRefundQueryModel model)
            throws AlipayApiException {
        // 实例化客户端
        AlipayClient alipayClient = initUtil.newAliClient();
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        request.setBizModel(model);
        return alipayClient.execute(request);
    }

    /**
     * 订单结算,用于在线下场景交易支付后，进行结算
     * @return AlipayTradeOrderSettleResponse
     * @throws AlipayApiException
     */
    public AlipayTradeOrderSettleResponse orderSettle(AlipayTradeOrderSettleModel model)
            throws AlipayApiException {
        // 实例化客户端
        AlipayClient alipayClient = initUtil.newAliClient();
        AlipayTradeOrderSettleRequest request = new AlipayTradeOrderSettleRequest();
        request.setBizModel(model);
        AlipayTradeOrderSettleResponse response = alipayClient.execute(request);
        LOG.info("订单结算{}: 订单信息:{},返回信息:{}", response.isSuccess() ? SUCCESS : FAIL,
                JSON.toJSONString(model), JSON.toJSONString(response));
        return response;
    }

    /**
     * 退款申请 当交易发生之后一段时间内，由于买家或者卖家的原因需要退款时，卖家可以通过退款接口将支付款退还给买家，
     * 支付宝将在收到退款请求并且验证成功之后，按照退款规则将支付款按原路退到买家帐号上。 交易超过约定时间（签约时设置的可退款时间）的订单无法进行退款
     * 支付宝退款支持单笔交易分多次退款，多次退款需要提交原支付订单的商户订单号和设置不同的退款单号。 一笔退款失败后重新提交，要采用原来的退款单号。总退款金额不能超过用户实际支付金额
     * @return AlipayTradeRefundResponse
     * @throws AlipayApiException
     */
    public AlipayTradeRefundResponse refund(AlipayTradeRefundModel model)
            throws AlipayApiException {
        // 实例化客户端
        AlipayClient alipayClient = initUtil.newAliClient();
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizModel(model);
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        LOG.info("订单退款请求{}: 订单信息:{},返回信息:{}", response.isSuccess() ? SUCCESS : FAIL,
                JSON.toJSONString(model), JSON.toJSONString(response));
        return response;
    }

    /**
     * 线下交易预创建,收银员通过收银台或商户后台调用支付宝接口，生成二维码后，展示给用户，由用户扫描二维码完成订单支付。
     * @return AlipayTradePrecreateResponse
     * @throws AlipayApiException
     */
    public AlipayTradePrecreateResponse tradePrecreate(AlipayTradePrecreateModel model)
            throws AlipayApiException {
        // 实例化客户端
        AlipayClient alipayClient = initUtil.newAliClient();
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(alipayConfig.getNotifyUrl() + alipayConfig.getPayBackUrl());// 支付结果回调地址
        request.setBizModel(model);
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        LOG.info("订单预创建{}: 订单信息:{},返回信息:{}", response.isSuccess() ? SUCCESS : FAIL,
                JSON.toJSONString(model), JSON.toJSONString(response));
        return response;
    }

    /**
     * 账单下载链接查询
     * @return AlipayDataDataserviceBillDownloadurlQueryResponse
     * @throws AlipayApiException
     */
    public AlipayDataDataserviceBillDownloadurlQueryResponse billDownloadUrlQuery(AlipayDataDataserviceBillDownloadurlQueryModel model)
            throws AlipayApiException {
        // 实例化客户端
        AlipayClient alipayClient = initUtil.newAliClient();
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();// 创建API对应的request类
        request.setBizModel(model);// 设置业务参数
        return alipayClient.execute(request);
    }


    /**
     * 转账
     * @param model
     * @return   AlipayFundTransToaccountTransferResponse
     * @throws AlipayApiException
     * @see
     */
    public AlipayFundTransToaccountTransferResponse transferAccounts(AlipayFundTransToaccountTransferModel model) throws AlipayApiException {
        // 实例化客户端
        AlipayClient alipayClient = initUtil.newAliClient();
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        request.setBizModel(model);
        AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
        LOG.info("转账操作{}: 订单信息:{},返回信息:{}", response.isSuccess() ? SUCCESS : FAIL,
                JSON.toJSONString(model), JSON.toJSONString(response));
        return response;
    }

    /**
     * 转账订单查询
     * @param model
     * @return  AlipayFundTransOrderQueryResponse
     * @throws AlipayApiException
     * @see
     */
    public AlipayFundTransOrderQueryResponse transOrderQuery(AlipayFundTransOrderQueryModel model) throws AlipayApiException {
        // 实例化客户端
        AlipayClient alipayClient = initUtil.newAliClient();
        AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
        request.setBizModel(model);
        AlipayFundTransOrderQueryResponse response = alipayClient.execute(request);
        LOG.info("转账查询{}: 订单信息:{},返回信息:{}", response.isSuccess() ? SUCCESS : FAIL,
                JSON.toJSONString(model), JSON.toJSONString(response));
        return response;
    }

    /**
     * 创建支付宝订单,商户通过该接口进行交易的创建下单 必传参数: out_trade_no:商户订单号,
     * total_amount:订单金额,buyer_id:买家的支付宝唯一用户号（2088开头的16位纯数字）,
     * 和buyer_logon_id(买家支付宝帐号)不能同时为空subject:订单标题
     *
     * @param model
     * @return
     * @throws AlipayApiException
     */
    public AlipayTradeCreateResponse createOrder(AlipayTradeCreateModel model) throws AlipayApiException {
        AlipayClient alipayClient = initUtil.newAliClient();
        AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
        request.setNotifyUrl(alipayConfig.getNotifyUrl() + alipayConfig.getPayBackUrl());
        request.setBizModel(model);
        AlipayTradeCreateResponse response = alipayClient.execute(request);
        LOG.info("订单创建{}: 订单信息:{},返回信息:{}", response.isSuccess() ? SUCCESS : FAIL,
                JSON.toJSONString(model), JSON.toJSONString(response));
        return response;
    }

    /**
     * web支付 电脑网页支付AlipayTradePagePayResponse.body为支付跳转url
     *
     * @param model
     * @return
     * @throws AlipayApiException
     */
    public AlipayTradePagePayResponse webPay(AlipayTradePagePayModel model)
            throws AlipayApiException {
        AlipayClient alipayClient = initUtil.newAliClient();
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(alipayConfig.getNotifyUrl() + alipayConfig.getPayBackUrl());// 支付结果回调地址
        request.setReturnUrl(JSON.parseObject(model.getPassbackParams()).getString("retUrl"));
        try {
            model.setPassbackParams(URLEncoder.encode(
                    model.getPassbackParams() == null ? "" : model.getPassbackParams(),
                    alipayConfig.getCharset()));
        } catch (Exception e) {
            LOG.error("web支付URLEncoder编码出现异常 ,异常信息：{}", e);
        }
        request.setBizModel(model);
        AlipayTradePagePayResponse response = alipayClient.sdkExecute(request);
        LOG.info("wab支付签名{}: 订单信息:{},返回信息:{}", response.isSuccess() ? SUCCESS : FAIL,
                JSON.toJSONString(model), JSON.toJSONString(response));
        response.setBody(alipayConfig.getUrl() + "?" + response.getBody());
        return response;
    }

    /**
     * app支付 AlipayTradeAppPayResponse.body为参数加密后的orderString
     * @param model
     * @return
     * @throws AlipayApiException
     */
    public AlipayTradeAppPayResponse appPay(AlipayTradeAppPayModel model) throws AlipayApiException {
        // 实例化客户端
        AlipayClient alipayClient = initUtil.newAliClient();
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setNotifyUrl(alipayConfig.getNotifyUrl() + alipayConfig.getPayBackUrl());// 支付结果回调地址
        try {
            model.setPassbackParams(URLEncoder.encode(
                    model.getPassbackParams() == null ? "" : model.getPassbackParams(),
                    alipayConfig.getCharset()));
        }
        catch (Exception e) {
            LOG.error("web支付URLEncoder编码出现异常 ,异常信息：{}", e);
        }
        request.setBizModel(model);
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
        LOG.info("app支付签名{}: 订单信息:{},返回信息:{}", response.isSuccess() ? SUCCESS : FAIL,
                JSON.toJSONString(model), JSON.toJSONString(response));
        return response;
    }

    /**
     * wap支付,返回支付页面:AlipayTradeAppPayResponse.body为参数加密后的orderString
     * @param model
     * @return AlipayTradeWapPayResponse
     * @throws AlipayApiException
     * @see
     */
    public AlipayTradeWapPayResponse wapPay(AlipayTradeWapPayModel model) throws AlipayApiException {
        AlipayClient alipayClient = initUtil.newAliClient();
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setNotifyUrl(alipayConfig.getNotifyUrl() + alipayConfig.getPayBackUrl());// 支付结果回调地址
        try {
            model.setPassbackParams(URLEncoder.encode(
                    model.getPassbackParams() == null ? "" : model.getPassbackParams(),
                    alipayConfig.getCharset()));
        }
        catch (Exception e) {
            LOG.error("wap支付URLEncoder编码出现异常 ,异常信息：{}", e);
        }
        request.setBizModel(model);
        AlipayTradeWapPayResponse response = alipayClient.sdkExecute(request);
        LOG.info("wap支付签名{}: 订单信息:{},返回信息:{}", response.isSuccess() ? SUCCESS : FAIL,
                JSON.toJSONString(model), JSON.toJSONString(response));
        return response;
    }

}
