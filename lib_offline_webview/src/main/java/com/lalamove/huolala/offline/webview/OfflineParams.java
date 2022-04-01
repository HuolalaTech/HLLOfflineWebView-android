package com.lalamove.huolala.offline.webview;

import com.lalamove.huolala.offline.webview.Interceptor.IInterceptor;
import com.lalamove.huolala.offline.webview.download.IDownLoader;
import com.lalamove.huolala.offline.webview.flow.IFlowResultHandleStrategy;
import com.lalamove.huolala.offline.webview.log.Logger;
import com.lalamove.huolala.offline.webview.matcher.BisNameMatcher;
import com.lalamove.huolala.offline.webview.monitor.base.IEnhWebMonitor;
import com.lalamove.huolala.offline.webview.net.IOfflineRequest;
import com.lalamove.huolala.offline.webview.threadpool.IExecutorServiceProvider;


public class OfflineParams {

    Logger mLogger;

    IExecutorServiceProvider mExecutorProvider;

    boolean mIsDebug;

    IDownLoader mDownLoader;

    BisNameMatcher mMatcher;

    IInterceptor mInterceptor;

    IFlowResultHandleStrategy mFlowResultHandleStrategy;

    OfflineConfig mOfflineConfig;

    IOfflineRequest mRequest;

    IEnhWebMonitor mMonitor;

    public OfflineParams config(OfflineConfig offlineConfig) {
        mOfflineConfig = offlineConfig;
        return this;
    }

    public OfflineParams logger(Logger logger) {
        mLogger = logger;
        return this;
    }

    public OfflineParams isDebug(boolean isDebug) {
        mIsDebug = isDebug;
        return this;
    }

    public OfflineParams downloader(IDownLoader downLoader) {
        mDownLoader = downLoader;
        return this;
    }

    public OfflineParams matcher(BisNameMatcher matcher) {
        mMatcher = matcher;
        return this;
    }

    public OfflineParams executorServiceProvider(IExecutorServiceProvider provider) {
        mExecutorProvider = provider;
        return this;
    }

    public OfflineParams interceptor(IInterceptor interceptor) {
        mInterceptor = interceptor;
        return this;
    }

    public OfflineParams flowResultHandleStrategy(IFlowResultHandleStrategy flowResultHandleStrategy) {
        mFlowResultHandleStrategy = flowResultHandleStrategy;
        return this;
    }


    public OfflineParams requestServer(IOfflineRequest request) {
        mRequest = request;
        return this;
    }

    public OfflineParams monitor(IEnhWebMonitor monitor) {
        mMonitor = monitor;
        return this;
    }
}