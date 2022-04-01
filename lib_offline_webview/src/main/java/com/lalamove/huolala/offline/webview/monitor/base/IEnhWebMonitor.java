package com.lalamove.huolala.offline.webview.monitor.base;

import java.util.HashMap;

/**
 * create by zhii.yang 2021/12/3
 * desc :
 */

public interface IEnhWebMonitor {

    /**
     * 埋点
     */
    void report(String event, HashMap<String, Object> params);

    /**
     * 监控分析
     */
    void monitorSummary(String name, float value, HashMap<String, String> labelMap, String extra);
}
