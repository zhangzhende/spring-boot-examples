package com.zzd.redis.model;

import java.io.Serializable;

/**
 * @Description 说明类的用途
 * @ClassName User
 * @Author zzd
 * @Create 2019/7/10 14:16
 * @Version 1.0
 **/
public class User implements Serializable {
    private static final long serialVersionUID = -9185033125448678735L;
    private String username;

    private String sex;

    private String pwd;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
