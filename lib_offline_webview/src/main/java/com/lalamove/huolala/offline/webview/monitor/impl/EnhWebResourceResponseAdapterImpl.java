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
        WebResourceResponse var10000;
        if (this.webkitResponse != null) {
            var10000 = this.webkitResponse;
            return var10000.getStatusCode();
        } else if (this.smttResponse != null) {
            var10000 = this.smttResponse;
            return var10000.getStatusCode();
        } else {
            return -1;
        }
    }

    @Override
    @Nullable
    public String getReasonPhrase() {
        WebResourceResponse var10000;
        if (this.webkitResponse != null) {
            var10000 = this.webkitResponse;
            return var10000.getReasonPhrase();
        } else if (this.smttResponse != null) {
            var10000 = this.smttResponse;
            return var10000.getReasonPhrase();
        } else {
            return null;
        }
    }

    public EnhWebResourceResponseAdapterImpl(@Nullable WebResourceResponse webkitResponse, @Nullable WebResourceResponse smttResponse) {
        this.webkitResponse = webkitResponse;
        this.smttResponse = smttResponse;
    }

}
