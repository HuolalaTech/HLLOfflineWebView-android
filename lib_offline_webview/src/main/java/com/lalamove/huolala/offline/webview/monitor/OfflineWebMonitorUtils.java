package com.lalamove.huolala.offline.webview.monitor;

import android.text.TextUtils;

import com.lalamove.huolala.offline.webview.OfflineWebManager;
import com.lalamove.huolala.offline.webview.flow.FlowReportParams;
import com.lalamove.huolala.offline.webview.monitor.base.IEnhWebMonitor;
import com.lalamove.huolala.offline.webview.utils.EnhWebUriUtils;

import java.util.HashMap;

/**
 * create by zhii.yang 2021/12/6
 * desc : 监控降级配置
 */

public class OfflineWebMonitorUtils {


    private OfflineWebMonitorUtils() {
    }

    public static void reportFlowParams(FlowReportParams params) {
        HashMap<String, Object> hashMap = new HashMap<>(12);
        hashMap.put("bisName", params.getBisName());
        hashMap.put("queryTime", params.getQueryTime());
        hashMap.put("queryResult", params.getQueryResult());
        hashMap.put("queryMsg", params.getQueryMsg());
        hashMap.put("downloadTime", params.getDownloadTime());
        hashMap.put("downloadResult", params.getDownloadResult());
        hashMap.put("downloadMsg", params.getDownloadMsg());
        hashMap.put("unzipTime", params.getUnzipTime());
        hashMap.put("unzipResult", params.getUnzipResult());
        hashMap.put("unzipMsg", params.getUnzipMsg());
        hashMap.put("zipSize", params.getZipSize());
        hashMap.put("continueDownload", params.isBrokenDown() ? 1 : 0);
        IEnhWebMonitor monitor = OfflineWebManager.getInstance().getMonitor();
        if (monitor != null) {
            monitor.report("offweb_cost_time", hashMap);
        }
    }

    public static void reportLoadError(
            String simpleUrl,
            String originUrl,
            String bisName,
            boolean isOffline,
            long startTime,
            String error,
            String errorCode
    ) {
        HashMap<String, Object> hashMap = new HashMap<>(8);
        hashMap.put("simpleUrl", simpleUrl);
        hashMap.put("url", originUrl);
        hashMap.put("bisName", bisName);
        hashMap.put("isOffweb", isOffline ? "1" : "0");
        hashMap.put("loadResult", "-1");
        hashMap.put("errMsg", error);
        hashMap.put("errCode", errorCode);
        hashMap.put("loadTime", System.currentTimeMillis() - startTime);
        IEnhWebMonitor monitor = OfflineWebManager.getInstance().getMonitor();
        if (monitor != null) {
            monitor.report("offweb_client_load_time", hashMap);
        }
    }

    public static void reportLoadFinish(
            String simpleUrl,
            String originUrl,
            String bisName,
            boolean isOffline,
            long startTime
    ) {
        HashMap<String, Object> hashMap = new HashMap<>(7);
        hashMap.put("simpleUrl", simpleUrl);
        hashMap.put("url", originUrl);
        hashMap.put("bisName", bisName);
        hashMap.put("isOffweb", isOffline ? "1" : "0");
        hashMap.put("loadResult", "0");
        hashMap.put("errMsg", "");
        hashMap.put("loadTime", System.currentTimeMillis() - startTime);
        IEnhWebMonitor monitor = OfflineWebManager.getInstance().getMonitor();
        if (monitor != null) {
            monitor.report("offweb_client_load_time", hashMap);
        }
    }

    /**
     * 请求速度（单位ms）
     */
    public static void monitorLoadTime(
            String originUrl,
            String bisName,
            boolean isOffline,
            long startTime,
            String resultCode
    ) {
        HashMap<String, Object> hashMap = new HashMap<>(4);
        //(网络请求为https，走离线包为file)
        hashMap.put("scheme", isOffline? "file": EnhWebUriUtils.getScheme(originUrl));
        hashMap.put("url", EnhWebUriUtils.getMonitorUrl(originUrl));
        hashMap.put("bisName", TextUtils.isEmpty(bisName) ? "" : bisName);
        hashMap.put("result", TextUtils.isEmpty(resultCode) ? "-1" : resultCode);
        IEnhWebMonitor monitor = OfflineWebManager.getInstance().getMonitor();
        if (monitor != null) {
            monitor.monitorSummary(
                    "webviewLoadTime",
                    (System.currentTimeMillis() - startTime),
                    hashMap,
                    ""
            );
        }
    }
}
