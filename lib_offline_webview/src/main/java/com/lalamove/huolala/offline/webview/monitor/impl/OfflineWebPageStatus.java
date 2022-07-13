package com.lalamove.huolala.offline.webview.monitor.impl;

import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.lalamove.huolala.offline.webview.OfflineWebManager;
import com.lalamove.huolala.offline.webview.log.OfflineWebLog;
import com.lalamove.huolala.offline.webview.monitor.OfflineWebMonitorUtils;
import com.lalamove.huolala.offline.webview.monitor.base.IEnhWebResourceErrorAdapter;
import com.lalamove.huolala.offline.webview.monitor.base.IEnhWebResourceRequestAdapter;
import com.lalamove.huolala.offline.webview.monitor.base.IEnhWebResourceResponseAdapter;
import com.lalamove.huolala.offline.webview.monitor.base.IWebPageStatus;
import com.lalamove.huolala.offline.webview.utils.OfflineConstant;
import com.lalamove.huolala.offline.webview.utils.OfflineStringUtils;

/**
 * @author yz
 * @time 2021/12/12 5:39 下午
 * 页面状态
 */
public final class OfflineWebPageStatus implements IWebPageStatus {
    private final String TAG = "OfflineWebPageStatus";
    private String mBisName;
    private String mOriginUrl;
    private boolean mIsOffline;
    private long mStartTime;
    private boolean mIsLoadSuccess;
    private boolean mIsLoadFail;
    /**
     * 过滤非一级页面，其他页面暂时不参与统计
     */
    private boolean mIsLoadFinish;

    @Override
    public void onLoadUrl(@Nullable String url) {
        if (!checkMonitorDisable() && isHttpUrl(
                url == null ? "" : url)
        ) {
            mOriginUrl = url;
            try {
                Uri uri = Uri.parse(url.trim());
                mBisName = uri.getQueryParameter(OfflineConstant.OFF_WEB);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mIsOffline = isOffWebUrl(url) && !OfflineWebManager.getInstance().getOfflineConfig().isDisable(mBisName);
            mStartTime = System.currentTimeMillis();
            mIsLoadFail = false;
            mIsLoadSuccess = false;
            mIsLoadFinish = false;
        }

    }

    private boolean isOffWebUrl(String url) {
        if (url != null) {
            boolean isOffWeb = false;
            if (OfflineWebManager.getInstance().isInit()) {
                isOffWeb = url.equals(OfflineWebManager.getInstance().getOfflineRes(url));
            }
            return url.contains(OfflineConstant.OFF_WEB) && isHttpUrl(url) && isOffWeb;
        }
        return false;
    }

    private boolean isHttpUrl(String url) {
        return (url.startsWith("http") || url.startsWith("https"));
    }

    @Override
    public void onPageLoadFinish(@Nullable String url, int progress) {
        OfflineWebLog.d(TAG,
                "onPageLoadFinish -> startTime --" + this.mStartTime
                        + "  progress --" + progress);
        if (checkStatus() || progress != 100) {
            return;
        }
        try {
            Uri parse = Uri.parse(url);
            String bisName = parse.getQueryParameter(OfflineConstant.OFF_WEB);
            if (isEquals(bisName, mBisName)) {
                OfflineWebMonitorUtils.reportLoadFinish(getSimpleUrl(), mOriginUrl, mBisName, mIsOffline, mStartTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!mIsLoadFail) {
            //这边走成功的计数监控
            OfflineWebMonitorUtils.monitorLoadTime(mOriginUrl, mBisName, mIsOffline, mStartTime, "0");
        }
        loadSuccessStatus();
    }

    private boolean isEquals(String bisName, String orgBisName) {
        return (TextUtils.isEmpty(orgBisName) && TextUtils.isEmpty(bisName)) ||
                (!TextUtils.isEmpty(orgBisName) && orgBisName.equals(bisName));
    }

    private String getSimpleUrl() {
        try {
            Uri parse = Uri.parse(mOriginUrl);
            return parse.getScheme() + "://" + parse.getHost() + parse.getPath();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void onLoadError(@Nullable IEnhWebResourceRequestAdapter request, @Nullable IEnhWebResourceErrorAdapter error) {
        if (checkStatus() || request == null || error == null) {
            return;
        }
        OfflineWebLog.d(this.TAG,
                "onLoadError -> startTime --" + mStartTime
                        + " WebResourceError --> " + error.getErrorCode()
                        + "  " + error.getDescription()
                        + " url --> " + request.getUrl());
        Uri uri = request.getUrl();
        if (uri == null) {
            return;
        }
        String bisName = uri.getQueryParameter("offweb");
        String errorStr = (new StringBuilder()).append("code = ")
                .append(error.getErrorCode())
                .append(", desc = ")
                .append(error.getDescription())
                .toString();
        if (isEquals(bisName, mBisName)) {
            OfflineWebMonitorUtils.reportLoadError(
                    this.getSimpleUrl(),
                    this.mOriginUrl,
                    this.mBisName,
                    this.mIsOffline,
                    this.mStartTime,
                    errorStr,
                    String.valueOf(error.getErrorCode()));
            OfflineWebMonitorUtils.monitorLoadTime(
                    this.mOriginUrl,
                    this.mBisName,
                    this.mIsOffline,
                    this.mStartTime,
                    String.valueOf(error.getErrorCode()));
        } else {
            this.offlineErrorMonitor(errorStr, String.valueOf(error.getErrorCode()));
        }
        loadFailStatus();
    }

    @Override
    public void onLoadError(@Nullable IEnhWebResourceRequestAdapter request, @Nullable IEnhWebResourceResponseAdapter error) {
        if (checkStatus() || request == null || error == null) {
            return;
        }
        OfflineWebLog.d(this.TAG, "onLoadError -> startTime --"
                + this.mStartTime + " WebResourceResponse --> "
                + error.getStatusCode() + "  "
                + error.getReasonPhrase()
                + " url --> " + request.getUrl());
        Uri uri = request.getUrl();
        if (uri == null) {
            return;
        }
        String bisName = uri.getQueryParameter(OfflineConstant.OFF_WEB);
        String errorStr = "code = " + error.getStatusCode() + ", desc = " + error.getReasonPhrase();
        if (isEquals(bisName, mBisName)) {
            OfflineWebMonitorUtils.reportLoadError(
                    this.getSimpleUrl(),
                    this.mOriginUrl,
                    this.mBisName,
                    this.mIsOffline,
                    this.mStartTime,
                    errorStr,
                    String.valueOf(error.getStatusCode()));
            OfflineWebMonitorUtils.monitorLoadTime(
                    this.mOriginUrl,
                    this.mBisName,
                    this.mIsOffline,
                    this.mStartTime,
                    String.valueOf(error.getStatusCode()));
        } else {
            offlineErrorMonitor(errorStr, String.valueOf(error.getStatusCode()));
        }
        loadFailStatus();

    }

    @Override
    public void onLoadError(@Nullable String url, @Nullable String error) {
        if (checkStatus() || TextUtils.isEmpty(url) || TextUtils.isEmpty(error)) {
            return;
        }
        OfflineWebLog.d(this.TAG, "onLoadError -> startTime --" + this.mStartTime + " error --> " + error + " url --> " + url);
        try {
            Uri uri = Uri.parse(url.trim());
            if (uri == null) {
                return;
            }
            String bisName = uri.getQueryParameter(OfflineConstant.OFF_WEB);
            if (isEquals(bisName, mBisName)) {
                OfflineWebMonitorUtils.reportLoadError(
                        getSimpleUrl(),
                        mOriginUrl,
                        mBisName,
                        mIsOffline,
                        mStartTime,
                        error,
                        "-1");
                OfflineWebMonitorUtils.monitorLoadTime(
                        mOriginUrl,
                        mBisName,
                        mIsOffline,
                        mStartTime,
                        "-1");
            } else {
                offlineErrorMonitor(error, "-1");

            }
        } catch (Exception var14) {
            OfflineWebLog.e(TAG, OfflineStringUtils.getErrorString((Throwable) var14));
        }
        loadFailStatus();
    }

    private final void offlineErrorMonitor(String error, String resultCode) {
        if (isLoadErrorMonitor()) {
            OfflineWebMonitorUtils.reportLoadError(
                    getSimpleUrl(),
                    mOriginUrl,
                    mBisName,
                    mIsOffline,
                    mStartTime,
                    error,
                    resultCode);
            OfflineWebMonitorUtils.monitorLoadTime(
                    mOriginUrl,
                    mBisName,
                    mIsOffline,
                    mStartTime,
                    resultCode);
        }

    }

    private final boolean isLoadErrorMonitor() {
        return !this.mIsLoadFail && !this.mIsLoadSuccess;
    }

    private final boolean checkStatus() {
        return this.checkMonitorDisable() || this.mStartTime == 0L || this.mIsLoadFinish;
    }

    private final boolean checkMonitorDisable() {
//      return MonitorDemotionConfig.INSTANCE.isDisableMonitor();
        return false;
    }

    private final void loadFailStatus() {
        this.mIsLoadFail = true;
        this.mIsLoadSuccess = false;
    }

    private final void loadSuccessStatus() {
        this.mIsLoadFail = false;
        this.mIsLoadSuccess = true;
        this.mIsLoadFinish = true;
    }
}