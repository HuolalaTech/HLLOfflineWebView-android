package com.lalamove.huolala.offline.webview.proxy;

import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.lalamove.huolala.offline.webview.log.OfflineWebLog;
import com.lalamove.huolala.offline.webview.task.CheckAndUpdateTask;
import com.lalamove.huolala.offline.webview.utils.OffWebRuleUtil;
import com.lalamove.huolala.offline.webview.utils.OfflineHandlerUtils;
import com.lalamove.huolala.offline.webview.utils.OfflineConstant;
import com.lalamove.huolala.offline.webview.OfflineWebManager;
import com.lalamove.huolala.offline.webview.widget.IOfflineWebView;
import com.lalamove.huolala.offline.webview.widget.ReloadOfflineWebView;


/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: OfflineWebViewProxy
 * @author: kelvin
 * @date: 7/22/21
 * @description:
 * @history:
 */

public class OfflineWebViewProxy implements IOfflineWebViewProxy {

    private static final String TAG = OfflineWebViewProxy.class.getSimpleName();
    private IOfflineWebView mIOfflineWebView;
    private String mBisName;
    private boolean mIsOffline;

    public OfflineWebViewProxy(IOfflineWebView iOfflineWebView) {
        mIOfflineWebView = iOfflineWebView;
    }

    @Override
    public String getBisName() {
        return mBisName;
    }

    @Override
    public String loadUrl(String url) {
        url = OffWebRuleUtil.addOfflineParam(url);
        String offlineRes = url;
        if (isOffWebUrl(url)) {
            try {
                Uri uri = Uri.parse(url.trim());
                mBisName = uri.getQueryParameter(OfflineConstant.OFF_WEB);
                if (!"http".equalsIgnoreCase(uri.getScheme()) && !"https".equalsIgnoreCase(uri.getScheme())) {
                    return url;
                }
            } catch (Exception e) {
                OfflineWebLog.e(TAG, e);
            }

            OfflineWebLog.i(TAG, url);
            if (TextUtils.isEmpty(mBisName)) {
                return url;
            }
            if (OfflineWebManager.getInstance().getOfflineConfig().isDisable(mBisName)) {
                OfflineWebLog.i(TAG, "is disabled:" + mBisName);
                return url;
            }

            offlineRes = OfflineWebManager.getInstance().getOfflineRes(url);
            OfflineWebLog.i(TAG, "match url :" + offlineRes);
            OfflineWebManager.getInstance().getExecutor().execute(new CheckAndUpdateTask(mBisName, null));

            mIsOffline = !url.equals(offlineRes);
            if (OfflineWebManager.getInstance().isDebug()) {
                Toast.makeText(OfflineWebManager.getInstance().getContext(),
                        mIsOffline ? "离线包模式" : "在线模式", Toast.LENGTH_LONG)
                        .show();
            }
        }

        if (mIsOffline && reloadEnable()) {
            OfflineWebLog.i(TAG, "add reload " + mBisName);
            OfflineWebManager.getInstance().getPageManager().addPage(this);
        }

        return offlineRes;
    }

    private boolean isOffWebUrl(String url) {
        return url.contains(OfflineConstant.OFF_WEB) && (url.startsWith("http") || url.startsWith("https"));
    }

    private boolean reloadEnable() {
        return mIOfflineWebView instanceof ReloadOfflineWebView && OfflineWebManager.getInstance().isForceReloadEnable();
    }

    @Override
    public void reLoadUrl() {
        OfflineHandlerUtils.post(new Runnable() {
            @Override
            public void run() {
                if (reloadEnable() && mIOfflineWebView != null) {
                    OfflineWebLog.i(TAG, "reLoadUrl");
                    ReloadOfflineWebView reloadOfflineWebView = (ReloadOfflineWebView) mIOfflineWebView;
                    reloadOfflineWebView.reloadOfflineWeb();
                }
            }
        });
    }

    @Override
    public void destroy() {
        if (reloadEnable()) {
            OfflineWebLog.i(TAG, "destroy");
            OfflineWebManager.getInstance().getPageManager().remove(this);
        }
    }

}
