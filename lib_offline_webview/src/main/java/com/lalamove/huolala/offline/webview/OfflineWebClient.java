package com.lalamove.huolala.offline.webview;

import android.content.Context;
import android.text.TextUtils;

import com.lalamove.huolala.offline.webview.resource.ResourceFlow;


/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: OfflineWebClient
 * @author: kelvin
 * @date: 3/30/22
 * @description: 对外提供
 * @history:
 */

public class OfflineWebClient {

    private OfflineWebClient() {
    }

    public static void init(Context context, OfflineParams offlineParams) {
        OfflineWebManager.getInstance().init(context, offlineParams);
        if (OfflineWebManager.getInstance().isInit()) {
            OfflineTaskManager.startInitTask();
        }
    }

    public static void checkPackage(final String bisName) {
        checkPackage(bisName,null);
    }
    /**
     * 根据业务名称拉取离线包
     *
     * @param bisName 业务名
     */
    public static void checkPackage(final String bisName, final ResourceFlow.FlowListener listener) {
        if (TextUtils.isEmpty(bisName)) {
            if (listener!=null) {
                listener.error(null,new IllegalStateException("bisName == null"));
            }
            return;
        }
        if (!OfflineWebManager.getInstance().isInit()) {
            if (listener!=null) {
                listener.error(null,new IllegalStateException("please init sdk first ."));
            }
            return;
        }
        OfflineTaskManager.checkPackage(bisName,listener);
    }

    public static void clean() {
        if (!OfflineWebManager.getInstance().isInit()) {
            return;
        }
        OfflineTaskManager.clean();
    }


}
