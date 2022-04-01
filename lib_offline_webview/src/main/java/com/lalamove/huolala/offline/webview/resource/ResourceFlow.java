package com.lalamove.huolala.offline.webview.resource;

import com.lalamove.huolala.offline.webview.OfflineWebManager;
import com.lalamove.huolala.offline.webview.info.OfflinePackageInfo;
import com.lalamove.huolala.offline.webview.log.OfflineWebLog;
import com.lalamove.huolala.offline.webview.flow.FlowReportParams;

import java.util.ArrayList;
import java.util.List;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: ResourceFlow
 * @author: kelvin
 * @date: 7/19/21
 * @description: 获取，下载，解压，替换离线包流程管理类
 * @history:
 */

public class ResourceFlow {
    private static final String TAG = ResourceFlow.class.getSimpleName();

    private List<IFlow> mFlows;

    private int mIndex;
    private OfflinePackageInfo mPackageInfo;
    private boolean isForceStop;
    private FlowListener mFlowListener;
    private FlowReportParams mReportParams;

    public ResourceFlow(OfflinePackageInfo packageInfo) {

        mPackageInfo = packageInfo;
        mReportParams = new FlowReportParams(mPackageInfo.getBisName());
    }

    public void stop() {
        isForceStop = true;
    }

    public void addFlow(IFlow flow) {
        if (mFlows == null) {
            mFlows = new ArrayList<>();
        }
        mFlows.add(flow);
    }

    public void start() {
        if (mFlows == null || mFlows.size() == 0) {
            return;
        }
        mIndex = 0;
        process();
    }

    void process() {

        if (mIndex >= mFlows.size()) {
            OfflineWebLog.i(TAG, "done ... ...");
            setDone();
            return;
        }

        if (isForceStop) {
            OfflineWebLog.i(TAG, "isForceStop  = " + isForceStop);
            return;
        }

        final IFlow iFlow = mFlows.get(mIndex++);
        OfflineWebManager.getInstance().getExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    OfflineWebLog.i(TAG, "Flow start process :" + iFlow.getClass().getSimpleName());
                    iFlow.process();
                } catch (FlowException e) {
                    e.printStackTrace();
                    OfflineWebLog.e(TAG, e);
                    error(e);
                }
            }
        });
    }

    void error(Throwable throwable) {
        OfflineWebLog.e(TAG, throwable);
        if (mFlowListener != null) {
            mFlowListener.error(mPackageInfo, throwable);
        }
        OfflineWebManager.getInstance().getFlowResultHandleStrategy().done(mReportParams);
    }

    public FlowReportParams getReportParams() {
        return this.mReportParams;
    }

    public OfflinePackageInfo getPackageInfo() {
        return mPackageInfo;
    }

    public void setPackageInfo(OfflinePackageInfo packageInfo) {
        mPackageInfo = packageInfo;
    }

    public void setFlowListener(FlowListener flowListener) {

        mFlowListener = flowListener;
    }

    public void setDone() {
        if (mFlowListener != null) {
            mFlowListener.done(mPackageInfo);
        }
        OfflineWebManager.getInstance().getFlowResultHandleStrategy().done(mReportParams);
    }

    public interface FlowListener {
        void done(OfflinePackageInfo packageInfo);

        void error(OfflinePackageInfo packageInfo, Throwable e);
    }

    public interface IFlow {
        void process() throws FlowException;
    }

    public static class FlowException extends Exception {

    }
}
