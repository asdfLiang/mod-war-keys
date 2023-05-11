package com.example.back.support.exceptions;
/**
 * @since 2023/5/7 12:27
 * @author by liangzj
 */
public class HotKeyConflictException extends RuntimeException {

    public HotKeyConflictException(String message) {
        super(message);
    }
}
