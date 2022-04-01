package com.lalamove.huolala.offline.webview.widget;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SafeBrowsingResponse;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;

import com.lalamove.huolala.offline.webview.monitor.base.IWebPageStatus;
import com.lalamove.huolala.offline.webview.monitor.impl.EnhWebResourceErrorAdapterImpl;
import com.lalamove.huolala.offline.webview.monitor.impl.EnhWebResourceRequestAdapterImpl;
import com.lalamove.huolala.offline.webview.monitor.impl.EnhWebResourceResponseAdapterImpl;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: EnhWebViewClient
 * @author: kelvin
 * @date: 3/24/22
 * @description:
 * @history:
 */

public class EnhWebViewClient extends WebViewClient {

    private WebViewClient mDelegate;
    private final IWebPageStatus mWebPageStatus;

    public EnhWebViewClient( IWebPageStatus webPageStatus) {
        mWebPageStatus = webPageStatus;
    }

    public void setDelegate(WebViewClient delegate){
        mDelegate = delegate;
    }

    @Override
    public void onLoadResource(WebView webView, String url) {
        if (mDelegate != null) {
            mDelegate.onLoadResource(webView, url);
        } else {
            super.onLoadResource(webView, url);
        }
    }

    @Override
    public void onPageFinished(WebView webView, String url) {
        if (mDelegate != null) {
            mDelegate.onPageFinished(webView, url);
        } else {
            super.onPageFinished(webView, url);
        }
        mWebPageStatus.onPageLoadFinish(url, webView.getProgress());
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (mDelegate != null) {
            return mDelegate.shouldOverrideUrlLoading(view, url);
        } else {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mDelegate != null) {
            mDelegate.onPageStarted(view, url, favicon);
        } else {
            super.onPageStarted(view, url, favicon);
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        if (mDelegate != null) {
            mDelegate.onReceivedError(view, errorCode, description, failingUrl);
        } else {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
        String errorStr = "code = " + errorCode + ", desc = " + description;
        mWebPageStatus.onLoadError(failingUrl, errorStr);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        if (mDelegate != null) {
            mDelegate.onReceivedError(view, request, error);
        } else {
            super.onReceivedError(view, request, error);
        }
        mWebPageStatus.onLoadError(request == null ? null : new EnhWebResourceRequestAdapterImpl(null, request),
                error == null ? null : new EnhWebResourceErrorAdapterImpl(null, error));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        if (mDelegate != null) {
            mDelegate.onReceivedHttpError(view, request, errorResponse);
        } else {
            super.onReceivedHttpError(view, request, errorResponse);
        }
        mWebPageStatus.onLoadError(request == null ? null : new EnhWebResourceRequestAdapterImpl(null, request),
                errorResponse == null ? null : new EnhWebResourceResponseAdapterImpl(null, errorResponse));
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        if (mDelegate != null) {
            mDelegate.doUpdateVisitedHistory(view, url, isReload);
        } else {
            super.doUpdateVisitedHistory(view, url, isReload);
        }
    }

    @Override
    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        if (mDelegate != null) {
            mDelegate.onFormResubmission(view, dontResend, resend);
        } else {
            super.onFormResubmission(view, dontResend, resend);
        }
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        if (mDelegate != null) {
            mDelegate.onReceivedHttpAuthRequest(view, handler, host, realm);
        } else {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }
    }

    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        if (mDelegate != null) {
            mDelegate.onReceivedClientCertRequest(view, request);
        } else {
            super.onReceivedClientCertRequest(view, request);
        }
    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        if (mDelegate != null) {
            mDelegate.onScaleChanged(view, oldScale, newScale);
        } else {
            super.onScaleChanged(view, oldScale, newScale);
        }
    }

    @Override
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        if (mDelegate != null) {
            mDelegate.onUnhandledKeyEvent(view, event);
        } else {
            super.onUnhandledKeyEvent(view, event);
        }
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        if (mDelegate != null) {
            return mDelegate.shouldOverrideKeyEvent(view, event);
        } else {
            return super.shouldOverrideKeyEvent(view, event);
        }
    }

    @Override
    public void onTooManyRedirects(WebView webView, Message cancelMsg, Message continueMsg) {
        if (mDelegate != null) {
            mDelegate.onTooManyRedirects(webView, cancelMsg, continueMsg);
        } else {
            super.onTooManyRedirects(webView, cancelMsg, continueMsg);
        }
    }

    @Override
    public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
        if (mDelegate != null) {
            mDelegate.onReceivedLoginRequest(view, realm, account, args);
        } else {
            super.onReceivedLoginRequest(view, realm, account, args);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onPageCommitVisible(WebView view, String url) {
        if (mDelegate != null) {
            mDelegate.onPageCommitVisible(view, url);
        } else {
            super.onPageCommitVisible(view, url);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
        if (mDelegate != null) {
            return mDelegate.onRenderProcessGone(view, detail);
        } else {
            return super.onRenderProcessGone(view, detail);
        }
    }


    @Override
    @Deprecated
    public WebResourceResponse shouldInterceptRequest(WebView view,
                                                      String url) {
        if (mDelegate != null) {
            return mDelegate.shouldInterceptRequest(view, url);
        } else {
            return super.shouldInterceptRequest(view, url);
        }
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view,
                                                      WebResourceRequest request) {
        if (mDelegate != null) {
            return mDelegate.shouldInterceptRequest(view, request.getUrl().toString());
        } else {
            return super.shouldInterceptRequest(view, request.getUrl().toString());
        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                   SslError error) {
        if (mDelegate != null) {
            mDelegate.onReceivedSslError(view, handler, error);
        } else {
            super.onReceivedSslError(view, handler, error);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    @Override
    public void onSafeBrowsingHit(WebView view, WebResourceRequest request, int threatType, SafeBrowsingResponse callback) {
        if (mDelegate != null) {
            mDelegate.onSafeBrowsingHit(view, request, threatType, callback);
        } else {
            super.onSafeBrowsingHit(view, request, threatType, callback);
        }
    }
}
