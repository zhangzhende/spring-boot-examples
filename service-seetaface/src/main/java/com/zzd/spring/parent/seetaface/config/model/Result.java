package com.zzd.spring.parent.seetaface.config.model;

import com.seetaface2.model.RecognizeResult;

import java.io.Serializable;

/**
 * @Author Sugar
 * @Version 2019/4/22 17:50
 */
public class Result implements Serializable {
    private String key;
    private float similar;
    private Integer index;

    public Result() {

    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Result(RecognizeResult result) {
        this.similar = result.similar;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public float getSimilar() {
        return similar;
    }

    public void setSimilar(float similar) {
        this.similar = similar;
    }

    @Override
    public String toString() {
        return "Result{" +
                "key='" + key + '\'' +
                ", similar=" + similar +
                ", index=" + index +
                '}';
    }
}
