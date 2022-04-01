package com.lalamove.huolala.offline.webview.flow;

/**
 * create by zhii.yang 2021/12/12
 * desc :
 */
public interface IFlowResultHandleStrategy {

    /**
     * 下载更新流程执行完毕回调
     * @param params 更新包过程中的数据
     */
    void done(FlowReportParams params);

}
