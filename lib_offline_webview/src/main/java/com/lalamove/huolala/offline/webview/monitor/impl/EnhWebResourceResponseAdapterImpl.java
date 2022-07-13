package com.lalamove.huolala.offline.webview.monitor.impl;

import android.webkit.WebResourceResponse;

import androidx.annotation.Nullable;

import com.lalamove.huolala.offline.webview.monitor.base.IEnhWebResourceResponseAdapter;

public final class EnhWebResourceResponseAdapterImpl implements IEnhWebResourceResponseAdapter {
    @Nullable
    private WebResourceResponse webkitResponse;
    @Nullable
    private WebResourceResponse smttResponse;

    @Override
    public int getStatusCode() {
        WebResourceResponse resourceResponse;
        if (this.webkitResponse != null) {
            resourceResponse = this.webkitResponse;
            return resourceResponse.getStatusCode();
        } else if (this.smttResponse != null) {
            resourceResponse = this.smttResponse;
            return resourceResponse.getStatusCode();
        } else {
            return -1;
        }
    }

    @Override
    @Nullable
    public String getReasonPhrase() {
        WebResourceResponse webResourceResponse;
        if (this.webkitResponse != null) {
            webResourceResponse = this.webkitResponse;
            return webResourceResponse.getReasonPhrase();
        } else if (this.smttResponse != null) {
            webResourceResponse = this.smttResponse;
            return webResourceResponse.getReasonPhrase();
        } else {
            return null;
        }
    }

    public EnhWebResourceResponseAdapterImpl(@Nullable WebResourceResponse webkitResponse, @Nullable WebResourceResponse smttResponse) {
        this.webkitResponse = webkitResponse;
        this.smttResponse = smttResponse;
    }

}
