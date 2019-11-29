package com.zzd.spark.ebrealtimestreamprocessing.java;

import java.io.Serializable;

/**
 * @Description 说明类的用途
 * @ClassName AdTrendCountHistory
 * @Author zzd
 * @Date 2019/10/8 20:43
 * @Version 1.0
 **/
public class AdTrendCountHistory implements Serializable {
    private Long clickedCountHistory;

    public Long getClickedCountHistory() {
        return clickedCountHistory;
    }

    public void setClickedCountHistory(Long clickedCountHistory) {
        this.clickedCountHistory = clickedCountHistory;
    }
}
