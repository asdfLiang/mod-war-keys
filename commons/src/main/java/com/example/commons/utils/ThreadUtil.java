package com.example.commons.utils;
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
}
