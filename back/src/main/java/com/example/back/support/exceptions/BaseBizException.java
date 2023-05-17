package com.example.back.support.exceptions;

import com.example.commons.exceptions.BaseException;

/**
 * @since 2023/5/16 23:21
 * @author by liangzj
 */
public class BaseBizException extends BaseException {
    public BaseBizException(String message) {
        super(message);
    }
}
