package com.lalamove.huolala.client.offline_web.util;

import android.annotation.SuppressLint;


import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: IoThreadPool
 * @author: muye
 * @date: 2021/7/7
 * @description: io型数据库，用于网络请求
 * @history:
 */

public class IoThreadPool {

    private final static String TAG = "IoThreadPool";

    //参数初始化
    protected static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    //线程池最大容纳线程数
    protected static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

    private static final int TIME_OUT = 60;

    private static final int CORE_POOL_SIZE = 5;
    private ThreadPoolExecutor mThreadPoolExecutor;

    private static IoThreadPool sCompressThreadPool;

    private IoThreadPool() {
        initThreadPool();
    }

    public static IoThreadPool getInstance() {
        if (null == sCompressThreadPool) {
            synchronized (IoThreadPool.class) {
                if (null == sCompressThreadPool) {
                    sCompressThreadPool = new IoThreadPool();
                }
            }
        }
        return sCompressThreadPool;
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return mThreadPoolExecutor;
    }

    @SuppressLint("NewApi")
    private void initThreadPool() {
        final AtomicInteger mAtomicInteger = new AtomicInteger(1);
        SecurityManager var1 = System.getSecurityManager();
        final ThreadGroup group = var1 != null ? var1.getThreadGroup() : Thread.currentThread().getThreadGroup();
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(group, runnable, "track io-pool-thread-" + mAtomicInteger.getAndIncrement(), 0);
//                return new Thread(group, runnable, "track io-pool-thread-" + mAtomicInteger.getAndIncrement(), 1024 * 256);
            }
        };
        mThreadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, Math.max(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE),
                TIME_OUT, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(128),
                threadFactory);
        mThreadPoolExecutor.allowCoreThreadTimeOut(true);
        mThreadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                super.rejectedExecution(r, e);

            }
        });
    }

}
