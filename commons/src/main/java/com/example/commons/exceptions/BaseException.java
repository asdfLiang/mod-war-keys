package com.example.commons.exceptions;
/**
 * @since 2023/5/16 23:22
 * @author by liangzj
 */
public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
