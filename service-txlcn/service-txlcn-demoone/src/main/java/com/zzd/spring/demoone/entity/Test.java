package com.zzd.spring.demoone.entity;

import java.util.Date;

/**
 * Created by lorne on 2017/6/26.
 */

public class Test {


    private Integer id;

    private String name;

    private Date created;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
