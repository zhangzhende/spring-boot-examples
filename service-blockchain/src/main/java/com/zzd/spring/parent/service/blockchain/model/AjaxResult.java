package com.zzd.spring.parent.service.blockchain.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 返回模型
 * @authoer ZZD
 * @param <T>
 */
public class AjaxResult<T> implements Serializable {


    private static final long serialVersionUID = -6109660022143247168L;
    /**
     *
     */
    @ApiModelProperty(notes = "返回信息", name = "message", example = "ok")
    private String message;
    /**
     *
     */
    @ApiModelProperty(notes = "返回代码", name = "code", example = "0")
    private int code;
    /**
     *
     */
    @ApiModelProperty(notes = "返回数据", name = "data")
    private T data;

    /**
     *
     */
    public AjaxResult()
    {
        super();
    }
//    public AjaxResult(T data) {
//        super();
//        this.data=data;
//    }

    /**
     * @param message
     */
    public AjaxResult(String message) {
        this.message = message;
    }


    public AjaxResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


    public AjaxResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public AjaxResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "AjaxResult [message=" + message + ", code=" + code
                + ", data=" + data + "]";
    }

    public AjaxResult(String message, int code, T data) {
        super();
        this.message = message;
        this.code = code;
        this.data = data;
    }


}
