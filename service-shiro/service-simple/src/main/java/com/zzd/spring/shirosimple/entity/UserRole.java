package com.zzd.spring.shirosimple.entity;

import java.io.Serializable;

/**
 *
 * 到头来：用户关联角色，角色关联权限，来控制
 * Created by Administrator on 2019/4/16 0016.
 */
public class UserRole implements Serializable {

    private static final long serialVersionUID = -5747329963006848134L;
    private Long relaId;
    private Long userId;
    private Long roleId;

    public Long getRelaId() {
        return relaId;
    }

    public void setRelaId(Long relaId) {
        this.relaId = relaId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "relaId=" + relaId +
                ", userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}
