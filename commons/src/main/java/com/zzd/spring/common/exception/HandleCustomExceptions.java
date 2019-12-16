package com.zzd.spring.common.exception;


import com.zzd.spring.common.entity.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author sunhonglei
 */
@RestControllerAdvice(basePackages = "com.njxnet")
public class HandleCustomExceptions {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(HandleCustomExceptions.class);

    private static final String EXCEPTION_OCCURD = "an exception occured in application [code: {}] [exception: {}]";

    private static final String EXCEPTION_OCCURD_WITHSTACKTRACE = EXCEPTION_OCCURD.concat(" [stackTrace: {}]");

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public AjaxResult<String> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        StringBuilder sb = new StringBuilder();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            sb.append(error.getDefaultMessage() + ",");
        }
        String str = sb.toString();
        String msg = str.substring(0, str.length() - 1);
        AjaxResult<String> ajaxResult = new AjaxResult<>();
        ajaxResult.setCode(ResultStatusCode.PARAM_ERROR.getCode()+"");
        ajaxResult.setMessage(msg);
        return ajaxResult;
    }

    /**
     * 自定义异常处理
     *
     * @param exception
     * @return
     * @see
     */
    @ExceptionHandler(value = BaseException.class)
    private AjaxResult<String> handleBaseException(BaseException exception) {
        return processBaseException(exception);
    }

    /**
     * 异常处理
     *
     * @param exception
     * @see
     */
    private AjaxResult<String> processBaseException(BaseException exception) {
        AjaxResult<String> ajaxResult = new AjaxResult<>();
        Integer errorCode = exception.getCode();
        ajaxResult.setCode(errorCode+"");
        ajaxResult.setMessage(exception.getMessage());
        LOG.error(EXCEPTION_OCCURD, errorCode, exception);
        return ajaxResult;
    }

    /**
     * 全局异常处理
     *
     * @param exception
     * @return
     * @see
     */
    @ExceptionHandler(value = Exception.class)
    private AjaxResult<String> handleException(Exception exception) {
        AjaxResult<String> ajaxResult = new AjaxResult<>();
        Integer errorCode = ResultStatusCode.ERROR.getCode();
        ajaxResult.setCode(errorCode+"");
        ajaxResult.setMessage(ResultStatusCode.ERROR.getName());
        StackTraceElement[] rows = exception.getStackTrace();
        StringBuilder stackTrace = new StringBuilder();
        for (StackTraceElement row : rows) {
            stackTrace.append(row + System.getProperty("line.separator"));
        }
        LOG.error(EXCEPTION_OCCURD_WITHSTACKTRACE, errorCode, exception, stackTrace);

        return ajaxResult;
    }

}
