package com.lalamove.huolala.offline.webview;

import android.text.TextUtils;

import com.lalamove.huolala.offline.webview.resource.ResourceFlow;
import com.lalamove.huolala.offline.webview.task.CheckAndUpdateTask;
import com.lalamove.huolala.offline.webview.task.CheckVersionTask;
import com.lalamove.huolala.offline.webview.task.CleanTask;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: OfflineTaskManager
 * @author: kelvin
 * @date: 3/30/22
 * @description: 异步任务管理
 * @history:
 */

public class OfflineTaskManager {
    private OfflineTaskManager() {
    }

    static void startInitTask() {
        OfflineConfig offlineConfig = OfflineWebManager.getInstance().getOfflineConfig();
        if (offlineConfig.isOpen()) {
            checkAllVersion();
            if (offlineConfig.getPreDownloadList() != null && offlineConfig.getPreDownloadList().size() > 0) {
                for (String bisName : offlineConfig.getPreDownloadList()) {
                    checkPackage(bisName, null);
                }
            }
        }
    }

    /**
     * 检查上次安装的离线包，进行改名替换删除等逻辑
     */
    static void checkAllVersion() {
        OfflineWebManager.getInstance().getExecutor().execute(new CheckVersionTask());
    }

    /**
     * 根据业务名称拉取离线包
     *
     * @param bisName 业务名
     */
    static void checkPackage(final String bisName,final ResourceFlow.FlowListener listener) {
        if (TextUtils.isEmpty(bisName)) {
            if (listener!=null) {
                listener.error(null,new IllegalStateException("bisName == null"));
            }
            return;
        }
        OfflineWebManager.getInstance().getExecutor().execute(new CheckAndUpdateTask(bisName,listener));
    }

    /**
     * 清空所有离线包
     */
    static void clean() {
        OfflineWebManager.getInstance().getExecutor().execute(new CleanTask());
    }


}
