package com.zzd.spring.alipay.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/4/8 0008.
 */
public class PayOrderDTO implements Serializable {

    private static final long serialVersionUID = -1339977557454403849L;
    private String timeout;
    private String orderNum;
    private String productCode;
    private String totalAmout;
    private String title;
    private String passbackParams;
    private String payType;

    public PayOrderDTO(String orderNum, String productCode, String totalAmout, String title) {
        this.orderNum = orderNum;
        this.productCode = productCode;
        this.totalAmout = totalAmout;
        this.title = title;
    }

    public PayOrderDTO() {
    }

    @Override
    public String toString() {
        return "PayOrderDTO{" +
                "timeout='" + timeout + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", productCode='" + productCode + '\'' +
                ", totalAmout='" + totalAmout + '\'' +
                ", title='" + title + '\'' +
                ", passbackParams='" + passbackParams + '\'' +
                ", payType='" + payType + '\'' +
                '}';
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getTotalAmout() {
        return totalAmout;
    }

    public void setTotalAmout(String totalAmout) {
        this.totalAmout = totalAmout;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPassbackParams() {
        return passbackParams;
    }

    public void setPassbackParams(String passbackParams) {
        this.passbackParams = passbackParams;
    }
}
