package com.zzd.spring.elasticsearch.aspect;

import java.io.Serializable;

/**
 * @Description 说明类的用途
 * @ClassName ElkLogModel
 * @Author zzd
 * @Date 2019/11/21 16:54
 * @Version 1.0
 **/
public class ElkLogModel implements Serializable {

    private static final long serialVersionUID = 789666570068576712L;
    private String uri;

    private String ip;

    private String classmethod;

    private String param;

    private Long  executionTime;

    private String message;

    private String stackTrace;

    @Override
    public String toString() {
        return "{" +
                "uri='" + uri + '\'' +
                ", ip='" + ip + '\'' +
                ", classmethod='" + classmethod + '\'' +
                ", param='" + param + '\'' +
                ", executionTime=" + executionTime +
                ", message='" + message + '\'' +
                ", stackTrace='" + stackTrace + '\'' +
                '}';
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClassmethod() {
        return classmethod;
    }

    public void setClassmethod(String classmethod) {
        this.classmethod = classmethod;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
