package com.zzd.spring.shirosimple.model;

import com.zzd.spring.shirosimple.entity.User;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2019/4/17 0017.
 */
public class UserVo implements Serializable {

    private static final long serialVersionUID = 8313534481204385949L;
    private Long uid;       // 用户id
    private String uname;   // 登录名，不可改
    private String nick;    // 用户昵称，可改
    private Date created;   // 创建时间
    private Date updated;   // 修改时间
    private Set<String> roles = new HashSet<>();    //用户所有角色值，用于shiro做角色权限的判断
    private Set<String> perms = new HashSet<>();    //用户所有权限值，用于shiro做资源权限的判断

    public UserVo(User user) {
        this.uid = user.getUid();
        this.uname = user.getUname();
        this.nick = user.getNick();
        this.created = user.getCreated();
        this.updated = user.getUpdated();
        this.roles = user.getRoles();
        this.perms = user.getPerms();
    }

    public UserVo(Long uid, String uname, String nick, Date created, Date updated, Set<String> roles, Set<String> perms) {
        this.uid = uid;
        this.uname = uname;
        this.nick = nick;
        this.created = created;
        this.updated = updated;
        this.roles = roles;
        this.perms = perms;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "uid=" + uid +
                ", uname='" + uname + '\'' +
                ", nick='" + nick + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", roles=" + roles +
                ", perms=" + perms +
                '}';
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPerms() {
        return perms;
    }

    public void setPerms(Set<String> perms) {
        this.perms = perms;
    }
}
