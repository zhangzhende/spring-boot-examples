package com.zzd.service.arcsoft.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (TUserFace)实体类
 *
 * @author makejava
 * @since 2019-11-28 10:22:32
 */
public class TUserFace implements Serializable {
    private static final long serialVersionUID = -80964519901130665L;
    //主键
    private Integer id;
    //分组标识
    private Integer groupId;
    //人脸唯一标识
    private String faceId;
    //名字
    private String name;
    //年龄
    private Integer age;
    //性别
    private Integer gender;
    //电话号码
    private String phone;
    //人脸特征
    private Object faceFeature;
    //创建时间
    private Date createTime;
    //最近更新时间
    private Date updateTime;
    //照片地址
    private String filePath;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getFaceFeature() {
        return faceFeature;
    }

    public void setFaceFeature(Object faceFeature) {
        this.faceFeature = faceFeature;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}