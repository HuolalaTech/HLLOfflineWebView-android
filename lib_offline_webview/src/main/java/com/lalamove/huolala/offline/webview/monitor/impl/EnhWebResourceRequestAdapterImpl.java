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
        WebResourceRequest resourceRequest;
        if (this.webkitRequest != null) {
            resourceRequest = this.webkitRequest;
            return resourceRequest.getUrl();
        } else if (this.smttRequest != null) {
            resourceRequest = this.smttRequest;
            return resourceRequest.getUrl();
        } else {
            return null;
        }
    }

    @Override
    public boolean isForMainFrame() {
        WebResourceRequest resourceRequest;
        if (this.webkitRequest != null) {
            resourceRequest = this.webkitRequest;
            return resourceRequest.isForMainFrame();
        } else if (this.smttRequest != null) {
            resourceRequest = this.smttRequest;
            return resourceRequest.isForMainFrame();
        } else {
            return false;
        }
    }

    @Override
    public boolean hasGesture() {
        WebResourceRequest resourceRequest;
        if (this.webkitRequest != null) {
            resourceRequest = this.webkitRequest;
            return resourceRequest.hasGesture();
        } else if (this.smttRequest != null) {
            resourceRequest = this.smttRequest;
            return resourceRequest.hasGesture();
        } else {
            return false;
        }
    }

    @Override
    @Nullable
    public String getMethod() {
        WebResourceRequest resourceRequest;
        if (this.webkitRequest != null) {
            resourceRequest = this.webkitRequest;
            return resourceRequest.getMethod();
        } else if (this.smttRequest != null) {
            resourceRequest = this.smttRequest;
            return resourceRequest.getMethod();
        } else {
            return null;
        }
    }

    @Override
    @Nullable
    public Map<String, String> getRequestHeaders() {
        WebResourceRequest resourceRequest;
        if (this.webkitRequest != null) {
            resourceRequest = this.webkitRequest;
            return resourceRequest.getRequestHeaders();
        } else if (this.smttRequest != null) {
            resourceRequest = this.smttRequest;
            return resourceRequest.getRequestHeaders();
        } else {
            return null;
        }
    }


    public EnhWebResourceRequestAdapterImpl(@Nullable WebResourceRequest webkitRequest, @Nullable WebResourceRequest smttRequest) {
        this.webkitRequest = webkitRequest;
        this.smttRequest = smttRequest;
    }

}