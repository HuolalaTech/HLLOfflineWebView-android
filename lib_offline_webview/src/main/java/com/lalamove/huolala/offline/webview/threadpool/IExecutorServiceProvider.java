package com.lalamove.huolala.offline.webview.threadpool;

import java.util.concurrent.ExecutorService;

/**
 * create by zhii.yang 2021/12/16
 * desc :
 */
public interface IExecutorServiceProvider {

    /**
     * 获取线程池
     * @return 线程池
     */
    ExecutorService get();

}
