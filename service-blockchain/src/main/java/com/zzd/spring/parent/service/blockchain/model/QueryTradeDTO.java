package com.zzd.spring.parent.service.blockchain.model;

import java.io.Serializable;

/**
 * @Description 说明类的用途
 * @ClassName QueryTradeDTO
 * @Author zzd
 * @Date 2020/5/28 17:17
 * @Version 1.0
 **/
public class QueryTradeDTO implements Serializable {
    private String transHash;

    public String getTransHash() {
        return transHash;
    }

    public void setTransHash(String transHash) {
        this.transHash = transHash;
    }
}
