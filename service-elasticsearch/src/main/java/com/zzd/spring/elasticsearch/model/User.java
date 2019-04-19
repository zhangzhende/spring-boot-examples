package com.zzd.spring.elasticsearch.model;

import java.util.Date;

/**
 * Created by zhangzhende on 2018/4/27.
 */
public class User {
    private String user;
    private Date postDate;
    private String message;

    @Override
    public String toString() {
        return "User{" +
                "user='" + user + '\'' +
                ", postDate=" + postDate +
                ", message='" + message + '\'' +
                '}';
    }

    public User() {
    }

    public User(String user, Date postDate, String message) {
        this.user = user;
        this.postDate = postDate;
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
