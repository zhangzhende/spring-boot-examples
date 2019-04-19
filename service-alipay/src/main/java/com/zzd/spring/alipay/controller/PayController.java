package com.zzd.spring.alipay.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.zzd.spring.alipay.model.OrderPayCallbackDTO;
import com.zzd.spring.alipay.model.PayOrderDTO;
import com.zzd.spring.alipay.servcie.BusinessService;
import com.zzd.spring.common.entity.AjaxResult;
import com.zzd.spring.common.enumeration.Platform;
import com.zzd.spring.common.utils.AjaxResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Administrator on 2019/4/8 0008.
 */
@RestController
@RequestMapping(value = "/pay")
public class PayController {

    @Autowired
    private BusinessService businessService;

    /**
     * 订单付款接口,web付款
     *
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping(value = "/ali/web")
    public AjaxResult<String> payWebOrder() throws AlipayApiException {
        PayOrderDTO orderDTO = new PayOrderDTO(UUID.randomUUID().toString(),
                "FAST_INSTANT_TRADE_PAY", "0.1", "茶易余额充值");
        Map<String, String> map = new HashMap<>();
        map.put("retUrl", "http://www.baidu.com");//付款成功以后的跳转地址
        orderDTO.setPassbackParams(JSONObject.toJSONString(map));
        orderDTO.setPayType(Platform.WEB.getCode());
        String result = businessService.payOrder(orderDTO);
        AjaxResult<String> ajaxResult = new AjaxResult<>();
        ajaxResult = AjaxResultUtils.getTrueAjaxResult(ajaxResult);
        ajaxResult.setData(result);
        return ajaxResult;
    }

    /**
     * 订单付款，app付款
     *
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping(value = "/ali/app")
    public AjaxResult<String> payAppOrder() throws AlipayApiException {
        PayOrderDTO orderDTO = new PayOrderDTO(UUID.randomUUID().toString(),
                "QUICK_MSECURITY_PAY", "0.1", "茶易余额充值");
        Map<String, String> map = new HashMap<>();
        map.put("retUrl", "http://www.baidu.com");//
        orderDTO.setPassbackParams(JSONObject.toJSONString(map));
        orderDTO.setPayType(Platform.APP.getCode());
        String result = businessService.payOrder(orderDTO);
        AjaxResult<String> ajaxResult = new AjaxResult<>();
        ajaxResult = AjaxResultUtils.getTrueAjaxResult(ajaxResult);
        ajaxResult.setData(result);
        return ajaxResult;
    }

    /**
     * 支付回调接口，订单支付完成以后，支付宝需要告诉你
     * @return
     */
    @RequestMapping(value = "/order/callback")
    public AjaxResult<String> orderPayCallBack(HttpServletRequest request,@RequestBody Map<String,String> param) {
        Map<String, String[]> map=request.getParameterMap();
        AjaxResult<String> ajaxResult = new AjaxResult<>();
        //TODO 业务处理包括订单完成以后的库存啊，订单状态的什么的处理
//        String msg=param.toString();
        System.out.println("2222222222222222222");
        return ajaxResult;
    }

}
