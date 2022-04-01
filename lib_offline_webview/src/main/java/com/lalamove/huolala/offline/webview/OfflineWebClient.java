package com.lalamove.huolala.offline.webview;

import android.content.Context;
import android.text.TextUtils;


/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: OfflineWebClient
 * @author: kelvin
 * @date: 3/30/22
 * @description: 对外提供
 * @history:
 */

public class OfflineWebClient {

    public static void init(Context context, OfflineParams offlineParams) {
        OfflineWebManager.getInstance().init(context, offlineParams);
        if (OfflineWebManager.getInstance().isInit()) {
            OfflineTaskManager.startInitTask();
        }
    }

    /**
     * 根据业务名称拉取离线包
     *
     * @param bisName 业务名
     */
    public static void checkPackage(final String bisName) {
        if (TextUtils.isEmpty(bisName)) {
            return;
        }
        if (!OfflineWebManager.getInstance().isInit()) {
            return;
        }
        OfflineTaskManager.checkPackage(bisName);
    }

    public static void clean() {
        if (!OfflineWebManager.getInstance().isInit()) {
            return;
        }
        OfflineTaskManager.clean();
    }


}
