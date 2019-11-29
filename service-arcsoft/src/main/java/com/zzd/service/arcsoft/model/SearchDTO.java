package com.zzd.service.arcsoft.model;

import java.io.Serializable;

/**
 * @Description 说明类的用途
 * @ClassName f
 * @Author zzd
 * @Date 2019/11/28 10:33
 * @Version 1.0
 **/
public class SearchDTO implements Serializable {

    private Integer groupId;

    private String picture;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
