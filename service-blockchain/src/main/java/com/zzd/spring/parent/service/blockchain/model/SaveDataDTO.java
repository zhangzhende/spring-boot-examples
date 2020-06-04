package com.zzd.spring.parent.service.blockchain.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description 说明类的用途
 * @ClassName SaveDataDTO
 * @Author zzd
 * @Date 2020/5/27 16:21
 * @Version 1.0
 **/
public class SaveDataDTO implements Serializable {
    @ApiModelProperty(value = "合约地址",name = "contractAddress")
    private String contractAddress;
    @ApiModelProperty(value = "保存的key值",name = "key")
    private String key;
    @ApiModelProperty(value = "保存的内容",name = "content")
    private String content;

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
