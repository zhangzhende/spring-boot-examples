package com.zzd.spring.parent.service.blockchain.model;

import java.io.Serializable;

/**
 * @Description 说明类的用途
 * @ClassName ChainContent
 * @Author zzd
 * @Date 2020/5/28 13:41
 * @Version 1.0
 **/
public class ChainContent implements Serializable {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
