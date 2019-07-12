package com.zzd.spring.demoone.entity;

import java.io.Serializable;

/**
 * (TTest)实体类
 *
 * @author makejava
 * @since 2019-07-11 19:47:18
 */
public class TTest implements Serializable {
    private static final long serialVersionUID = 934439379094387290L;
    
    private Integer id;
    
    private String name;


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

}