package com.zzd.spring.normal.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/4/16 0016.
 */
public class RolePerm implements Serializable{


    private static final long serialVersionUID = 3651484879829246424L;
    private Long relaId;
    private Long roleId;
    private Long permId;

    public Long getRelaId() {
        return relaId;
    }

    public void setRelaId(Long relaId) {
        this.relaId = relaId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermId() {
        return permId;
    }

    public void setPermId(Long permId) {
        this.permId = permId;
    }

    @Override
    public String toString() {
        return "RolePerm{" +
                "relaId=" + relaId +
                ", roleId=" + roleId +
                ", permId=" + permId +
                '}';
    }
}
