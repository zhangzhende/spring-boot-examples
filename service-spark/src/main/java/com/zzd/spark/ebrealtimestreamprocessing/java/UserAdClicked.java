package com.zzd.spark.ebrealtimestreamprocessing.java;

import java.io.Serializable;

/**
 * @Description 说明类的用途
 * @ClassName UserAdClicked
 * @Author zzd
 * @Date 2019/10/8 20:48
 * @Version 1.0
 **/
public class UserAdClicked implements Serializable {

    private String timestamp;

    private String ip;

    private String userId;

    private String adId;

    private String province;

    private String city;

    private Long clickedCount;


    @Override
    public String toString() {
        return "UserAdClicked{" +
                "timestamp='" + timestamp + '\'' +
                ", ip='" + ip + '\'' +
                ", userId='" + userId + '\'' +
                ", adId='" + adId + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", clickedCount=" + clickedCount +
                '}';
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getClickedCount() {
        return clickedCount;
    }

    public void setClickedCount(Long clickedCount) {
        this.clickedCount = clickedCount;
    }
}
