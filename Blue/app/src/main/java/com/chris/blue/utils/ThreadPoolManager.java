package com.chris.blue.utils;

import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @date on 2019/1/3
 * @author Jeffrey Hou(Hou jie)
 * @email p_jiehhou@tencent.com
 * @describe TODO
 **/
public class ThreadPoolManager {
    private static final int SCHEDULED_CORE_POOL_SIZE = 10;
    private static final int NORMAL_CORE_POOL_SIZE = 2;
    private static final int NORMAL_MAX_POOL_SIZE = 5;
    private static final long NORMAL_KEEP_ALIVE_TIME = 10L;

    private static ThreadPoolManager sThreadPoolManager = null;
    private static ThreadPoolExecutor sThreadService = null;
    private static ScheduledExecutorService sScheduledService = null;

    private ThreadPoolManager() {
    }

    public synchronized static ThreadPoolManager getInstance() {
        if (sThreadPoolManager == null) {
            sThreadPoolManager = new ThreadPoolManager();
        }
        return sThreadPoolManager;
    }

    public synchronized void addTask(Runnable runnable) {
        getThreadService().submit(runnable);
    }

    private ThreadPoolExecutor getThreadService() {
        if (!isThreadServiceEnable()) {
            sThreadService = new ThreadPoolExecutor(NORMAL_CORE_POOL_SIZE, NORMAL_MAX_POOL_SIZE,
                    NORMAL_KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>());
        }
        return sThreadService;
    }

    private boolean isThreadServiceEnable() {
        return !(sThreadService == null
                || sThreadService.isShutdown() || sThreadService.isTerminated());
    }

    public synchronized void shutDown() {
        if (isThreadServiceEnable()) {
            getThreadService().shutdown();
        }

        if (isScheduledServiceEnable()) {
            getScheduledService().shutdown();
        }
    }

    private boolean isScheduledServiceEnable() {
        return !(sScheduledService == null
                || sScheduledService.isShutdown() || sScheduledService.isTerminated());
    }

    private ScheduledExecutorService getScheduledService() {
        if (!isScheduledServiceEnable()) {
            sScheduledService = new ScheduledThreadPoolExecutor(SCHEDULED_CORE_POOL_SIZE);
        }
        return sScheduledService;
    }

    public void shutDownNow() {
        if (isThreadServiceEnable()) {
            getThreadService().shutdownNow();
        }

        if (isScheduledServiceEnable()) {
            getScheduledService().shutdownNow();
        }
    }

    public ScheduledFuture<?> addScheduledTask(TimerTask timerTask,
                                           long initialDelay,
                                           long period,
                                           TimeUnit unit) {
        return getScheduledService().scheduleAtFixedRate(timerTask, initialDelay, period, unit);
    }

    public ScheduledFuture<?> addDelayTask(Runnable timerTask, long delay, TimeUnit unit) {
        return getScheduledService().schedule(timerTask, delay, unit);
    }
}
