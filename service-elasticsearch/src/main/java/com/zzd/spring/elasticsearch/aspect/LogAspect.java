package com.zzd.spring.elasticsearch.aspect;

import com.zzd.spring.common.exception.BaseException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Description 拦截接口调用信息，记录日志，异常信息记录
 * @ClassName LogAspect
 * @Author zzd
 * @Date 2019/11/21 15:15
 * @Version 1.0
 **/
@Component
@Aspect
public class LogAspect {
    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("(execution( * com.zzd.spring.elasticsearch.controller..*.*(..)))")
    public void logPointcut() {
    }

    @Around("logPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        LOG.info("=====================================Method  start====================================");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long end = System.currentTimeMillis();
            ElkLogModel logModel = new ElkLogModel();
            logModel.setUri(request.getRequestURI());
            logModel.setIp(request.getRemoteAddr());
            logModel.setClassmethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            logModel.setParam(Arrays.toString(joinPoint.getArgs()));
            logModel.setExecutionTime((end - start));
            LOG.info(logModel.toString());
            return result;
        } catch (Throwable e) {
            if(e instanceof BaseException){
                long end = System.currentTimeMillis();
                ElkLogModel logModel = new ElkLogModel();
                logModel.setUri(request.getRequestURI());
                logModel.setIp(request.getRemoteAddr());
                logModel.setClassmethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
                logModel.setParam(Arrays.toString(joinPoint.getArgs()));
                logModel.setExecutionTime((end - start));
                logModel.setMessage(e.getMessage());
//                异常堆栈
                StackTraceElement[] rows = e.getStackTrace();
                StringBuilder stackTrace = new StringBuilder();
                for (StackTraceElement row : rows) {
                    stackTrace.append(row + System.getProperty("line.separator"));
                }
                logModel.setStackTrace(stackTrace.toString());
                LOG.warn(logModel.toString());
                throw e;
            }
            long end = System.currentTimeMillis();
            ElkLogModel logModel = new ElkLogModel();
            logModel.setUri(request.getRequestURI());
            logModel.setIp(request.getRemoteAddr());
            logModel.setClassmethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            logModel.setParam(Arrays.toString(joinPoint.getArgs()));
            logModel.setExecutionTime((end - start));
            logModel.setMessage(e.getMessage());
            StackTraceElement[] rows = e.getStackTrace();
            StringBuilder stackTrace = new StringBuilder();
            for (StackTraceElement row : rows) {
                stackTrace.append(row + System.getProperty("line.separator"));
            }
            logModel.setStackTrace(stackTrace.toString());
            LOG.error(logModel.toString());
            throw e;
        }
    }
}
