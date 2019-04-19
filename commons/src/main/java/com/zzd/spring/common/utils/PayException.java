package com.zzd.spring.common.utils;

/**
 * 异常 〈功能详细描述〉
 * 
 * @see PayException
 * @since
 */
public class PayException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PayException() {}

    public PayException(String message) {
        super(message);
    }

    public PayException(String message, Throwable cause) {
        super(message, cause);
    }

    public PayException(Throwable cause) {
        super(cause);
    }
}
