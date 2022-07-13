package com.lalamove.huolala.offline.webview;

import android.text.TextUtils;

import com.lalamove.huolala.offline.webview.Interceptor.IInterceptor;
import com.lalamove.huolala.offline.webview.download.IDownLoader;
import com.lalamove.huolala.offline.webview.flow.IFlowResultHandleStrategy;
import com.lalamove.huolala.offline.webview.info.OfflineRuleConfig;
import com.lalamove.huolala.offline.webview.log.Logger;
import com.lalamove.huolala.offline.webview.matcher.BisNameMatcher;
import com.lalamove.huolala.offline.webview.monitor.base.IEnhWebMonitor;
import com.lalamove.huolala.offline.webview.net.IOfflineRequest;
import com.lalamove.huolala.offline.webview.threadpool.IExecutorServiceProvider;
import com.lalamove.huolala.offline.webview.utils.OfflineGsonUtils;

import java.util.List;


public class OfflineParams {

    private Logger mLogger;

    private IExecutorServiceProvider mExecutorProvider;

    private boolean mIsDebug;

    private IDownLoader mDownLoader;

    private BisNameMatcher mMatcher;

    private IInterceptor mInterceptor;

    private IFlowResultHandleStrategy mFlowResultHandleStrategy;

    private OfflineConfig mOfflineConfig;

    private IOfflineRequest mRequest;

    private IEnhWebMonitor mMonitor;

    private OfflineRuleConfig mOfflineRuleConfig;

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

    public OfflineParams setRule(OfflineRuleConfig offlineRuleConfig) {
        mOfflineRuleConfig = offlineRuleConfig;
        return this;
    }

    public OfflineParams setRule(String ruleJson) {
        mOfflineRuleConfig = OfflineGsonUtils.fromJson(ruleJson, OfflineRuleConfig.class);
        return this;
    }


    public OfflineParams addRule(String offweb, List<String> host, List<String> path, List<String> fragmentprefix) {
        if (TextUtils.isEmpty(offweb) ||
                host == null || host.size() == 0 ||
                path == null || path.size() == 0) {
            return this;
        }

        if (mOfflineRuleConfig == null) {
            mOfflineRuleConfig = new OfflineRuleConfig();
        }
        mOfflineRuleConfig.addRule(new OfflineRuleConfig.RulesInfo(offweb, host, path, fragmentprefix));
        return this;
    }

    public Logger getLogger() {
        return mLogger;
    }

    public IExecutorServiceProvider getExecutorProvider() {
        return mExecutorProvider;
    }

    public boolean isDebug() {
        return mIsDebug;
    }

    public IDownLoader getDownLoader() {
        return mDownLoader;
    }

    public BisNameMatcher getMatcher() {
        return mMatcher;
    }

    public IInterceptor getInterceptor() {
        return mInterceptor;
    }

    public IFlowResultHandleStrategy getFlowResultHandleStrategy() {
        return mFlowResultHandleStrategy;
    }

    public OfflineConfig getOfflineConfig() {
        return mOfflineConfig;
    }

    public IOfflineRequest getRequest() {
        return mRequest;
    }

    public IEnhWebMonitor getMonitor() {
        return mMonitor;
    }

    public OfflineRuleConfig getOfflineRuleConfig() {
        return mOfflineRuleConfig;
    }
}