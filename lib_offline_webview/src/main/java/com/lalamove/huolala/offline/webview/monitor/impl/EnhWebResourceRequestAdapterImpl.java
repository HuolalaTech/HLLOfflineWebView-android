package com.lalamove.huolala.offline.webview.monitor.impl;

import android.net.Uri;
import android.webkit.WebResourceRequest;

import androidx.annotation.Nullable;

import com.lalamove.huolala.offline.webview.monitor.base.IEnhWebResourceRequestAdapter;

import java.util.Map;

/**
 * create by zhii.yang 2021/12/27
 * desc :
 */
public final class EnhWebResourceRequestAdapterImpl implements IEnhWebResourceRequestAdapter {
    @Nullable
    private WebResourceRequest webkitRequest;
    @Nullable
    private WebResourceRequest smttRequest;

    @Override
    @Nullable
    public Uri getUrl() {
        WebResourceRequest var10000;
        if (this.webkitRequest != null) {
            var10000 = this.webkitRequest;
            return var10000.getUrl();
        } else if (this.smttRequest != null) {
            var10000 = this.smttRequest;
            return var10000.getUrl();
        } else {
            return null;
        }
    }

    public boolean isForMainFrame() {
        WebResourceRequest var10000;
        if (this.webkitRequest != null) {
            var10000 = this.webkitRequest;
            return var10000.isForMainFrame();
        } else if (this.smttRequest != null) {
            var10000 = this.smttRequest;
            return var10000.isForMainFrame();
        } else {
            return false;
        }
    }

    public boolean hasGesture() {
        WebResourceRequest var10000;
        if (this.webkitRequest != null) {
            var10000 = this.webkitRequest;
            return var10000.hasGesture();
        } else if (this.smttRequest != null) {
            var10000 = this.smttRequest;
            return var10000.hasGesture();
        } else {
            return false;
        }
    }

    @Override
    @Nullable
    public String getMethod() {
        WebResourceRequest var10000;
        if (this.webkitRequest != null) {
            var10000 = this.webkitRequest;
            return var10000.getMethod();
        } else if (this.smttRequest != null) {
            var10000 = this.smttRequest;
            return var10000.getMethod();
        } else {
            return null;
        }
    }

    @Override
    @Nullable
    public Map<String, String> getRequestHeaders() {
        WebResourceRequest var10000;
        if (this.webkitRequest != null) {
            var10000 = this.webkitRequest;
            return var10000.getRequestHeaders();
        } else if (this.smttRequest != null) {
            var10000 = this.smttRequest;
            return var10000.getRequestHeaders();
        } else {
            return null;
        }
    }


    public EnhWebResourceRequestAdapterImpl(@Nullable WebResourceRequest webkitRequest, @Nullable WebResourceRequest smttRequest) {
        this.webkitRequest = webkitRequest;
        this.smttRequest = smttRequest;
    }

}