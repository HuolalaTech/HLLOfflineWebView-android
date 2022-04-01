package com.lalamove.huolala.offline.webview.monitor.base;

/**
 * @author yz
 * @time 2021/12/12 5:39 下午
 * 页面状态
 */
public interface IWebPageStatus {
    void onLoadUrl(String url);

    void onPageLoadFinish(String url, int progress);

    void onLoadError(IEnhWebResourceRequestAdapter requestAdapter, IEnhWebResourceErrorAdapter error);

    void onLoadError(IEnhWebResourceRequestAdapter requestAdapter, IEnhWebResourceResponseAdapter errorResponse);

    void onLoadError(String url, String error);
}
