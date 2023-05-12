package com.example.commons.utils;

import java.util.concurrent.TimeUnit;

/**
 * @since 2023/4/30 23:39
 * @author by liangzj
 */
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
            throw new RuntimeException(e);
        }
    }
}
