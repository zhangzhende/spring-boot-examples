package com.zzd.service.arcsoft.model.dto;


import java.io.Serializable;

public class ProcessInfo implements Serializable {
    private Integer age;
    private Integer gender;

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
}
