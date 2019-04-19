package com.zzd.spring.shirosimple.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限点
 * Created by Administrator on 2019/4/16 0016.
 */
public class Perm implements Serializable{

    private static final long serialVersionUID = 1114636028586898710L;
    /**
     * 权限类型：菜单
     */
    public static int PTYPE_MENU = 1;
    /**
     * 权限类型：按钮
     */
    public static int PTYPE_BUTTON = 2;

    private Long pid;       // 权限id
    private String pname;   // 权限名称
    private Integer ptype;  // 权限类型：1.菜单；2.按钮
    private String pval;    // 权限值，shiro的权限控制表达式
    private Date created;   // 创建时间
    private Date updated;   // 修改时间

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getPtype() {
        return ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public String getPval() {
        return pval;
    }

    public void setPval(String pval) {
        this.pval = pval;
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

    @Override
    public String toString() {
        return "Perm{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", ptype=" + ptype +
                ", pval='" + pval + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
