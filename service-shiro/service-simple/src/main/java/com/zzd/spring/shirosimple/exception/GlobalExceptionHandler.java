package com.zzd.spring.shirosimple.exception;

import com.zzd.spring.common.entity.AjaxResult;
import com.zzd.spring.shirosimple.config.CustomRealm;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 全局统一异常处理
 * Created by Administrator on 2019/4/17 0017.
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //不满足@RequiresGuest注解时抛出的异常信息
    private static final String GUEST_ONLY = "Attempting to perform a guest-only operation";

    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public AjaxResult<String> handleShiroExcetion(ShiroException e) {
        String ename = e.getClass().getSimpleName();
        logger.error("shiro 执行出错：{}", ename);
        return new AjaxResult<String>(false, ShiroExceptionCode.SHIRO_ERR.getMessage(),
                ShiroExceptionCode.SHIRO_ERR.getCode(), null);
    }

    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseBody
    public AjaxResult<String> page401(UnauthenticatedException e) {
        String message = e.getMessage();
        if (StringUtils.startsWithIgnoreCase(message, GUEST_ONLY)) {
            return new AjaxResult<String>(false, ShiroExceptionCode.UNAUTHEN_GUEST_ONLY.getMessage(),
                    ShiroExceptionCode.UNAUTHEN_GUEST_ONLY.getCode(), null);
        } else {
            return new AjaxResult<String>(false, ShiroExceptionCode.UNAUTHEN.getMessage(),
                    ShiroExceptionCode.UNAUTHEN.getCode(), null);
        }
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public AjaxResult<String> page403() {
        return new AjaxResult<String>(false, ShiroExceptionCode.UNAUTHZ.getMessage(),
                ShiroExceptionCode.UNAUTHZ.getCode(), null);
    }

}
