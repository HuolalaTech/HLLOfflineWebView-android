package com.lalamove.huolala.offline.webview;

import android.content.Context;
import android.content.SharedPreferences;

import com.lalamove.huolala.offline.webview.Interceptor.DefaultInterceptor;
import com.lalamove.huolala.offline.webview.Interceptor.IInterceptor;
import com.lalamove.huolala.offline.webview.download.DefaultDownloader;
import com.lalamove.huolala.offline.webview.download.IDownLoader;
import com.lalamove.huolala.offline.webview.flow.FlowResultHandleStrategy;
import com.lalamove.huolala.offline.webview.flow.IFlowResultHandleStrategy;
import com.lalamove.huolala.offline.webview.log.OfflineWebLog;
import com.lalamove.huolala.offline.webview.log.Logger;
import com.lalamove.huolala.offline.webview.matcher.BisNameMatcher;
import com.lalamove.huolala.offline.webview.matcher.DefaultMatcher;
import com.lalamove.huolala.offline.webview.monitor.base.IEnhWebMonitor;
import com.lalamove.huolala.offline.webview.net.IOfflineRequest;
import com.lalamove.huolala.offline.webview.threadpool.IExecutorServiceProvider;
import com.lalamove.huolala.offline.webview.resource.ResourceFlow;
import com.lalamove.huolala.offline.webview.threadpool.OfflineIoThreadPool;
import com.lalamove.huolala.offline.webview.utils.OffWebRuleUtil;
import com.lalamove.huolala.offline.webview.utils.OfflineConstant;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: OfflineWebManager
 * @author: kelvin
 * @date: 7/19/21
 * @description: 初始化相关参数、配置管理类
 * @history:
 */

public class OfflineWebManager {

    private static final String TAG = OfflineWebManager.class.getSimpleName();

    private Context mContext;

    /**
     * 离线业务包，检测更新，下载，解压，替换 流程
     */
    private List<ResourceFlow> mResourceFlows = Collections.synchronizedList(new LinkedList<>());

    /**
     * 页面管理 用于强刷功能
     */
    private OfflinePageManager mPageManager = new OfflinePageManager();

    /**
     * 强刷开关
     */
    private boolean mForceReloadEnable = true;

    private OfflineConfig mOfflineConfig;

    /**
     * 下载器
     */
    private IDownLoader mDownLoader;

    /**
     * 线程池提供器
     */
    private IExecutorServiceProvider mExecutorProvider;

    /**
     * debug模式判断，影响日志输出，debug模式抛出异常会闪退
     */
    private boolean mIsDebug;

    /**
     * 日志输出
     */
    private Logger mLogger;

    /**
     * url 匹配器
     */
    private BisNameMatcher mMatcher;

    private IFlowResultHandleStrategy mFlowResultHandleStrategy;

    private boolean mIsInit;

    private IInterceptor mInterceptor;

    private IOfflineRequest mRequest;

    private IEnhWebMonitor mMonitor;

    private boolean mIsOpen;

    public static OfflineWebManager getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private final static OfflineWebManager INSTANCE = new OfflineWebManager();
    }

    private OfflineWebManager() {
    }

    public void init(Context context, OfflineParams offlineParams) {
        mContext = context;
        mRequest = offlineParams.getRequest();
        mOfflineConfig = offlineParams.getOfflineConfig();
        if (mRequest == null || mOfflineConfig == null) {
            if (offlineParams.isDebug()) {
                throw new IllegalStateException("please set base url and env");
            } else {
                OfflineWebLog.e(TAG, new IllegalStateException("please set base url and env"));
            }
        }
        mIsOpen = mOfflineConfig.isOpen();
        if (!mIsOpen) {
            mIsInit = false;
            return;
        }
        mDownLoader = offlineParams.getDownLoader();
        mExecutorProvider = offlineParams.getExecutorProvider();
        mIsDebug = offlineParams.isDebug();
        mLogger = offlineParams.getLogger();
        mMatcher = offlineParams.getMatcher();
        mInterceptor = offlineParams.getInterceptor() == null ? new DefaultInterceptor() : offlineParams.getInterceptor();
        mIsInit = true;
        mMonitor = offlineParams.getMonitor();
        mFlowResultHandleStrategy = offlineParams.getFlowResultHandleStrategy() == null
                ? new FlowResultHandleStrategy() : offlineParams.getFlowResultHandleStrategy();
        OffWebRuleUtil.init(offlineParams.getOfflineRuleConfig());
        OfflineWebLog.i(TAG, "init success ");
    }

    public OfflinePageManager getPageManager() {
        return mPageManager;
    }

    public boolean isForceReloadEnable() {
        return mForceReloadEnable;
    }

    public List<ResourceFlow> getResourceFlows() {
        return mResourceFlows;
    }

    public String getOfflineRes(String url) {
        OfflineWebLog.d(TAG, url);
        return getMatcher().matching(url);
    }

    public SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences(OfflineConstant.MODULE_SP_NAME, Context.MODE_PRIVATE);
    }

    public IDownLoader getDownLoader() {
        if (mDownLoader == null) {
            mDownLoader = new DefaultDownloader();
        }
        return mDownLoader;
    }

    public ExecutorService getExecutor() {
        if (mExecutorProvider == null) {
            return OfflineIoThreadPool.getInstance().getThreadPoolExecutor();
        }
        ExecutorService executorService = mExecutorProvider.get();
        if (executorService == null) {
            return OfflineIoThreadPool.getInstance().getThreadPoolExecutor();
        }
        return executorService;
    }

    public OfflineConfig getOfflineConfig() {
        return mOfflineConfig;
    }

    public boolean isDebug() {
        return mIsDebug;
    }

    public Logger getLogger() {
        return mLogger;
    }

    public BisNameMatcher getMatcher() {
        if (mMatcher == null) {
            mMatcher = new DefaultMatcher();
        }
        return mMatcher;
    }

    public Context getContext() {
        return mContext;
    }

    public boolean isInit() {
        return mIsInit && mIsOpen;
    }

    public IInterceptor getInterceptor() {
        return mInterceptor;
    }

    public IFlowResultHandleStrategy getFlowResultHandleStrategy() {
        return mFlowResultHandleStrategy;
    }

    public IOfflineRequest getRequest() {
        return mRequest;
    }

    public IEnhWebMonitor getMonitor() {
        return mMonitor;
    }
}
