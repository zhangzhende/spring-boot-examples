package com.zzd.spring.normal.handler;

import com.zzd.spring.common.entity.AjaxResult;
import com.zzd.spring.normal.enums.ResultStatusCode;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {
    private static final Logger log = LoggerFactory.getLogger(ExceptionAdvice.class);

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class, BindException.class,
            ServletRequestBindingException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public AjaxResult<String> handleHttpMessageNotReadableException(Exception e) {
        log.error("参数解析失败", e);
        if (e instanceof BindException) {
            return new AjaxResult<String>(ResultStatusCode.BAD_REQUEST.getCode(), ((BindException) e).getAllErrors().get(0).getDefaultMessage());
        }
        return new AjaxResult<String>(ResultStatusCode.BAD_REQUEST.getCode(), e.getMessage());
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法", e);
        return new AjaxResult<String>(ResultStatusCode.METHOD_NOT_ALLOWED.getCode(), ResultStatusCode.METHOD_NOT_ALLOWED.getMsg());
    }

    /**
     * shiro权限异常处理
     *
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    public AjaxResult<String> unauthorizedException(UnauthorizedException e) {
        log.error(e.getMessage(), e);

        return new AjaxResult<String>(ResultStatusCode.UNAUTHO_ERROR.getCode(), ResultStatusCode.UNAUTHO_ERROR.getMsg());
    }

    /**
     * 500
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public AjaxResult<String> handleException(Exception e) {
        e.printStackTrace();
        log.error("服务运行异常", e);
        return new AjaxResult<String>(ResultStatusCode.SYSTEM_ERR.getCode(), ResultStatusCode.SYSTEM_ERR.getMsg());
    }
}
