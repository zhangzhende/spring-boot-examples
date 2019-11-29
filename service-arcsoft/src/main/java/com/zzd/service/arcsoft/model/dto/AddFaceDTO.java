package com.zzd.service.arcsoft.model.dto;

import java.io.Serializable;

/**
 * @Description 说明类的用途
 * @ClassName AddFaceDTO
 * @Author zzd
 * @Date 2019/11/28 11:21
 * @Version 1.0
 **/
public class AddFaceDTO implements Serializable {

    private String picture;

    private Integer groupId;

    private String name;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
