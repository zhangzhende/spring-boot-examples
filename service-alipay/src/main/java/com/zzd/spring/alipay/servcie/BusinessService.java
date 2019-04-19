package com.zzd.spring.alipay.servcie;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.zzd.spring.alipay.model.PayOrderDTO;
import com.zzd.spring.common.entity.AjaxResult;
import com.zzd.spring.common.enumeration.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/4/8 0008.
 */
@Service
public class BusinessService {
    @Autowired
    private AliPayService aliPayService;

    /**
     * 订单付款
     *
     * @param payOrderDTO
     * @return
     * @throws AlipayApiException
     */
    public String payOrder(PayOrderDTO payOrderDTO) throws AlipayApiException {
        String url="";
        if (Platform.WEB.getCode().equalsIgnoreCase(payOrderDTO.getPayType())) {
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();
            model.setTimeExpire(payOrderDTO.getTimeout());
            model.setOutTradeNo(payOrderDTO.getOrderNum());
            model.setProductCode(payOrderDTO.getProductCode());
            model.setTotalAmount(payOrderDTO.getTotalAmout());
            model.setSubject(payOrderDTO.getTitle());
            model.setPassbackParams(payOrderDTO.getPassbackParams());
            AlipayTradePagePayResponse response = aliPayService.webPay(model);
            url = response.getBody();
        } else if (Platform.APP.getCode().equalsIgnoreCase(payOrderDTO.getPayType())) {
            AlipayTradeAppPayModel model =new AlipayTradeAppPayModel();
            model.setOutTradeNo(payOrderDTO.getOrderNum());
            model.setProductCode(payOrderDTO.getProductCode());
            model.setTotalAmount(payOrderDTO.getTotalAmout());
            model.setSubject(payOrderDTO.getTitle());
            model.setPassbackParams(payOrderDTO.getPassbackParams());
            AlipayTradeAppPayResponse response = aliPayService.appPay(model);
            url=response.getBody();
        }
        System.out.println(url);// url
        return url;
    }
}
