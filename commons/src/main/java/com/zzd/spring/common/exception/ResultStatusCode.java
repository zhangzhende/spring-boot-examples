package com.zzd.spring.common.exception;

/**
 * 返回状态码枚举
 */
public enum ResultStatusCode {

    SUCCESS(0, "操作成功"),
    FAIL(1,"操作失败"),
    ERROR(500, "服务器内部错误"),
    TOKEN_NOT_FOUND(1000,"用户token信息不存在"),
    MISSING_PARAM(1001,"参数缺失"),
    PARAM_ERROR(1002,"参数错误"),
    USER_HAS_NO_RIGHT(1003,"用户无权执行操作"),
    USER_EXIST(1004,"该用户已存在"),
    LOGIN_FAIL(1005,"登录失败，用户名或者密码错误"),
    DATA_EXIST(1006,"该数据已存在，请查实后在操作"),
    UNKNOWN_FILE(1007,"未知文件"),
    FILE_UPLOAD_FAIL(1008,"文件上传失败"),

    REQUEST_AUTH_FAILED(1009,"请求参数校验不通过"),
    NO_MAPPED_URL(1010,"无法找到产品代码对应的请求地址"),
    ;

    /**
     * 状态码
     */
    private int code;

    /**
     * 状态信息
     */
    private String name;

    ResultStatusCode(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
