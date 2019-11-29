package com.zzd.spark.ebrealtimestreamprocessing.java;

import java.io.Serializable;

/**
 * @Description 说明类的用途
 * @ClassName AdTrendStat
 * @Author zzd
 * @Date 2019/10/8 20:44
 * @Version 1.0
 **/
public class AdTrendStat implements Serializable {
    private String date;

    private String hour;

    private String minute;

    private  String adId;

    private Long clickedCount;

    @Override
    public String toString() {
        return "AdTrendStat{" +
                "date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", minute='" + minute + '\'' +
                ", adId='" + adId + '\'' +
                ", clickedCount=" + clickedCount +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public Long getClickedCount() {
        return clickedCount;
    }

    public void setClickedCount(Long clickedCount) {
        this.clickedCount = clickedCount;
    }
}
