package com.lalamove.huolala.offline.webview.task;

import com.lalamove.huolala.offline.webview.OfflineWebManager;
import com.lalamove.huolala.offline.webview.info.OfflinePackageInfo;
import com.lalamove.huolala.offline.webview.log.OfflineWebLog;
import com.lalamove.huolala.offline.webview.resource.DownloadFlow;
import com.lalamove.huolala.offline.webview.resource.FetchPackageFlow;
import com.lalamove.huolala.offline.webview.resource.ParsePackageFlow;
import com.lalamove.huolala.offline.webview.resource.ReplaceResFlow;
import com.lalamove.huolala.offline.webview.resource.ResourceFlow;
import com.lalamove.huolala.offline.webview.utils.OfflinePackageUtil;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: CheckAndUpdateTask
 * @author: kelvin
 * @date: 8/4/21
 * @description: 根据业务名称检查更新 离线包
 * @history:
 */

public class CheckAndUpdateTask implements Runnable {
    private static final String TAG = CheckAndUpdateTask.class.getSimpleName();
    private String mBisName;

    public CheckAndUpdateTask(String bisName) {
        mBisName = bisName;
    }

    /**
     * 单线程 线程池？
     */
    @Override
    public void run() {
        synchronized (CheckAndUpdateTask.class) {
            //当前bisName 是否在禁用列表
            if (OfflineWebManager.getInstance().getInterceptor().isIntercept(mBisName)) {
                OfflineWebLog.i(TAG, "interceptor bisName :" + mBisName);
                return;
            }
            //判断是否已经在更新
            for (ResourceFlow resourceFlow : OfflineWebManager.getInstance().getResourceFlows()) {
                if (mBisName.equals(resourceFlow.getPackageInfo().getBisName())) {
                    OfflineWebLog.i(TAG, "same flow running :" + mBisName);
                    return;
                }
            }
            //新建更新任务
            String packageVersion = OfflinePackageUtil.getPackageVersion(mBisName);
            OfflineWebLog.i(TAG, "checkPackage:" + mBisName + "，packageVersion：" + packageVersion);
            ResourceFlow resourceFlow = new ResourceFlow(new OfflinePackageInfo(mBisName, packageVersion));
            resourceFlow.addFlow(new FetchPackageFlow(resourceFlow));
            resourceFlow.addFlow(new DownloadFlow(resourceFlow));
            resourceFlow.addFlow(new ParsePackageFlow(resourceFlow));
            resourceFlow.addFlow(new ReplaceResFlow(resourceFlow));
            resourceFlow.setFlowListener(new ResourceFlow.FlowListener() {
                @Override
                public void done(OfflinePackageInfo packageInfo) {
                    OfflineWebLog.i(TAG, "done :");
                    doneFlow(packageInfo);
                }

                @Override
                public void error(OfflinePackageInfo packageInfo, Throwable throwable) {

                    doneFlow(packageInfo);
                    OfflineWebLog.i(TAG, "error");

                }

                private void doneFlow(OfflinePackageInfo packageInfo) {
                    //移除任务
                    for (ResourceFlow flow : OfflineWebManager.getInstance().getResourceFlows()) {
                        if (packageInfo.equals(flow.getPackageInfo())) {
                            OfflineWebManager.getInstance().getResourceFlows().remove(flow);
                            break;
                        }
                    }
                }
            });
            resourceFlow.start();
            OfflineWebManager.getInstance().getResourceFlows().add(resourceFlow);
        }
    }

}
