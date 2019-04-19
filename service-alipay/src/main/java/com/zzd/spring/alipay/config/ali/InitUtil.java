package com.zzd.spring.alipay.config.ali;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;


/**
 *
 */
@Component
public class InitUtil {
    /**
     * 阿里支付配置
     */
    @Autowired
    private AlipayConfig payConfig;

    /**
     * Description: <br> 1、…<br> 2、…<br> Implement: <br> 1、…<br> 2、…<br>
     * 
     * @return AlipayClient
     * @see
     */
    public AlipayClient newAliClient() {
        return new DefaultAlipayClient(payConfig.getUrl(), payConfig.getAppId(),
            payConfig.getRsaPrivateKey(), payConfig.getFormat(), payConfig.getCharset(),
            payConfig.getAliPayPublicKey(), payConfig.getSingType());
    }
}
