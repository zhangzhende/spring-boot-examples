package com.zzd.spring.alipay.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/9 0009.
 */
public class OrderPayCallbackDTO implements Serializable{

    private static final long serialVersionUID = 3546877124377503851L;
    private Map<String,String> retParams;


    public Map<String, String> getRetParams() {
        return retParams;
    }

    public void setRetParams(Map<String, String> retParams) {
        this.retParams = retParams;
    }

    @Override
    public String toString() {
        return "OrderPayCallbackDTO{" +
                "retParams=" + retParams +
                '}';
    }
}
