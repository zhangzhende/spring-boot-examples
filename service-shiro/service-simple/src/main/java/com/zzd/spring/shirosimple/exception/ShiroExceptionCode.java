/*
 * 文件名：ElasticSearchTypeName.java 版权：Copyright by www.newlixon.com/ 描述： 修改人：qi_baijian 修改时间：2018年8月24日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.zzd.spring.shirosimple.exception;

public enum ShiroExceptionCode {


    SHIRO_ERR("444", " 鉴权或授权过程出错"),


    UNAUTHEN("401", "未登录，无权访问"),

    UNAUTHEN_GUEST_ONLY("401", "只允许游客访问，若您已登录，请先退出登录"),

    SERVER_ERR("500", "服务端异常"),

    UNAUTHZ("403", "未授权，拒绝访问");

    /**
     * code
     */
    private String code;

    /**
     * message
     */
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ShiroExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
