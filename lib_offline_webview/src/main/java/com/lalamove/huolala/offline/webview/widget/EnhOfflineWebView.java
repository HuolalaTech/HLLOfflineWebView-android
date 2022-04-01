package com.lalamove.huolala.offline.webview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.lalamove.huolala.offline.webview.monitor.base.IEnhWebResourceErrorAdapter;
import com.lalamove.huolala.offline.webview.monitor.base.IEnhWebResourceRequestAdapter;
import com.lalamove.huolala.offline.webview.monitor.base.IEnhWebResourceResponseAdapter;
import com.lalamove.huolala.offline.webview.monitor.base.IWebPageStatus;
import com.lalamove.huolala.offline.webview.monitor.impl.OfflineWebPageStatus;
import com.lalamove.huolala.offline.webview.proxy.IOfflineWebViewProxy;
import com.lalamove.huolala.offline.webview.proxy.OffWebProxyFactory;

import java.util.Map;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: EnhOfflineWebView
 * @author: kelvin
 * @date: 7/22/21
 * @description: 增强webview
 * @history:
 */
public class EnhOfflineWebView extends WebView implements ReloadOfflineWebView, IWebPageStatus {

    private IOfflineWebViewProxy mOfflineWebViewProxy;
    private IWebPageStatus mWebPageStatus;
    private EnhWebViewClient mEnhWebViewClient;

    public EnhOfflineWebView(Context context) {
        super(context);
        initProxy();
    }

    public EnhOfflineWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initProxy();
    }

    public EnhOfflineWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initProxy();
    }

    public EnhOfflineWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initProxy();
    }

    private void initProxy() {
        mOfflineWebViewProxy = OffWebProxyFactory.getProxy(this);
        mWebPageStatus = new OfflineWebPageStatus();

        mEnhWebViewClient = new EnhWebViewClient(this);
        super.setWebViewClient(mEnhWebViewClient);
    }

    @Override
    public void setWebViewClient(WebViewClient webViewClient){
        mEnhWebViewClient.setDelegate(webViewClient);
    }

    @Override
    public void loadUrl(String url) {
        mWebPageStatus.onLoadUrl(url);
        super.loadUrl(mOfflineWebViewProxy.loadUrl(url));
    }

    @Override
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        mWebPageStatus.onLoadUrl(url);
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

    @Override
    public void onLoadError(IEnhWebResourceRequestAdapter request, IEnhWebResourceErrorAdapter error) {
        mWebPageStatus.onLoadError(request, error);
    }

    @Override
    public void onLoadError(IEnhWebResourceRequestAdapter request, IEnhWebResourceResponseAdapter errorResponse) {
        mWebPageStatus.onLoadError(request, errorResponse);
    }

    @Override
    public void onLoadError(String url, String error) {
        mWebPageStatus.onLoadError(url, error);
    }

    @Override
    public void onLoadUrl(@Nullable String url) {
        mWebPageStatus.onLoadUrl(url);
    }

    @Override
    public void onPageLoadFinish(@Nullable String url, int progress) {
        mWebPageStatus.onPageLoadFinish(url, progress);
    }
}
