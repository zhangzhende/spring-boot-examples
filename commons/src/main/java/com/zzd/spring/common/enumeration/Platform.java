package com.zzd.spring.common.enumeration;

/**
 * Created by Administrator on 2019/4/9 0009.
 */
public enum Platform {
    WEB("web", "网页端"),
    APP("app", "移动端");

    private String code;
    private String name;

    Platform(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
