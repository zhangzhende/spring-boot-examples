package com.zzd.spark.ebrealtimestreamprocessing.java;

import java.io.Serializable;

/**
 * @Description 说明类的用途
 * @ClassName AdClicked
 * @Author zzd
 * @Date 2019/10/8 20:46
 * @Version 1.0
 **/
public class AdClicked implements Serializable {

    private String timestamp;

    private String adId;

    private String province;

    private String city;

    private Long clickedCount;

    @Override
    public String toString() {
        return "AdClicked{" +
                "timestamp='" + timestamp + '\'' +
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
