package com.zzd.spring.parent.seetaface.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (TUserFaces)实体类
 *
 * @author makejava
 * @since 2019-12-03 10:26:11
 */
public class TUserFaces implements Serializable {
    private static final long serialVersionUID = -33417748215937916L;
    //主键标识
    private Integer id;
    //唯一标识UUID
    private String keyCode;
    //图片数据
    private byte[] imageData;
    //宽度
    private Integer width;
    //高度
    private Integer height;
    //通道
    private Integer channel;
    //名称
    private String name;
    //年龄
    private Integer age;
    //性别（0-女，1-男）
    private Integer gender;
    
    private Date createTime;
    
    private Date updateTime;
    //在人脸库中的索引
    private Integer faceIndex;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
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

    public Integer getFaceIndex() {
        return faceIndex;
    }

    public void setFaceIndex(Integer faceIndex) {
        this.faceIndex = faceIndex;
    }

}