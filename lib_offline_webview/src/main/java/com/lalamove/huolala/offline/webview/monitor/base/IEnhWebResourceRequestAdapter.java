package com.lalamove.huolala.offline.webview.monitor.base;

import android.net.Uri;

import java.util.Map;

/**
 * create by zhii.yang 2021/12/27
 * desc :
 */

public interface IEnhWebResourceRequestAdapter {
    Uri getUrl();

    boolean isForMainFrame();

    boolean hasGesture();

    String getMethod();

    Map<String, String> getRequestHeaders();
}
