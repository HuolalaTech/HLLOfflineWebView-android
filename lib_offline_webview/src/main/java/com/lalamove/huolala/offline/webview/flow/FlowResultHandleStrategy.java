package com.lalamove.huolala.offline.webview.flow;

import com.lalamove.huolala.offline.webview.log.OfflineWebLog;
import com.lalamove.huolala.offline.webview.monitor.OfflineWebMonitorUtils;

/**
 * create by zhii.yang 2021/12/12
 * desc :
 */

public class FlowResultHandleStrategy implements IFlowResultHandleStrategy {
    @Override
    public void done(FlowReportParams params) {
        OfflineWebLog.d(getClass().getSimpleName(), "FlowReportParams  --> " + params);
        if (params == null) {
            return;
        }
        OfflineWebMonitorUtils.reportFlowParams(params);
    }
}
