package com.zzd.spring.common.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/4/2 0002.
 */
public class AjaxResult<T>  implements Serializable {


    private static final long serialVersionUID = -6109660022143247168L;
    /**
     *
     */
    private Boolean success = true;
    /**
     *
     */
    private String message;
    /**
     *
     */
    private String code;
    /**
     *
     */
    private T data;

    /**
     *
     */
    public AjaxResult() {
        super();
    }

    /**
     * @param success
     */
    public AjaxResult(Boolean success) {
        this.setSuccess(success);
    }

    /**
     * @param message
     */
    public AjaxResult(String message) {
        this.message = message;
    }

    /**
     * @param success
     * @param message
     */
    public AjaxResult(Boolean success, String message) {
        this.setSuccess(success);
        this.message = message;
    }


    public AjaxResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "AjaxResult [success=" + success + ", message=" + message + ", code=" + code
                + ", data=" + data + "]";
    }

    public AjaxResult(Boolean success, String message, String code, T data) {
        super();
        this.success = success;
        this.message = message;
        this.code = code;
        this.data = data;
    }


}
