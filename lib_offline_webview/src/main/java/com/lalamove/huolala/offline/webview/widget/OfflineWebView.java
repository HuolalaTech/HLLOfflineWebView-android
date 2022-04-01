package com.lalamove.huolala.offline.webview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.lalamove.huolala.offline.webview.proxy.IOfflineWebViewProxy;
import com.lalamove.huolala.offline.webview.proxy.OffWebProxyFactory;

import java.util.Map;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: OfflineWebView
 * @author: kelvin
 * @date: 7/22/21
 * @description: 基础webview
 * @history:
 */
public class OfflineWebView extends WebView implements ReloadOfflineWebView {

    private IOfflineWebViewProxy mOfflineWebViewProxy;

    public OfflineWebView(Context context) {
        super(context);
        initProxy();
    }

    public OfflineWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initProxy();
    }

    public OfflineWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initProxy();
    }

    public OfflineWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initProxy();
    }

    private void initProxy() {
        mOfflineWebViewProxy = OffWebProxyFactory.getProxy(this);
    }

    @Override
    public void loadUrl(String url) {
        super.loadUrl(mOfflineWebViewProxy.loadUrl(url));
    }

    @Override
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        super.loadUrl(mOfflineWebViewProxy.loadUrl(url), additionalHttpHeaders);
    }

    @Override
    public void reloadOfflineWeb() {
        // refresh this web view
    }

    @Override
    public void destroy() {
        super.destroy();
        mOfflineWebViewProxy.destroy();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mOfflineWebViewProxy.destroy();
    }

}
