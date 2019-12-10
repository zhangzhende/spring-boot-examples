package com.zzd.spring.parent.seetaface.entity;

import java.io.Serializable;

/**
 * (TFaceIndex)实体类
 *
 * @author makejava
 * @since 2019-12-03 11:09:56
 */
public class TFaceIndex implements Serializable {
    private static final long serialVersionUID = 597239476854306016L;
    //脸索引
    private Integer faceIndex;
    //数据库字段标识
    private String keyCode;


    public Integer getFaceIndex() {
        return faceIndex;
    }

    public void setFaceIndex(Integer faceIndex) {
        this.faceIndex = faceIndex;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

}