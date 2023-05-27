package com.example.common.utils;

import com.example.common.exceptions.BaseException;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @since 2023/4/30 23:39
 * @author by liangzj
 */
@Slf4j
public class ThreadUtil {
    public static Thread newDaemonThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        return thread;
    }

    public static void sleep(Integer second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            log.error("sleep interrupted: second: {}", second, e);
            throw new BaseException("sleep interrupted");
        }
    }
}
