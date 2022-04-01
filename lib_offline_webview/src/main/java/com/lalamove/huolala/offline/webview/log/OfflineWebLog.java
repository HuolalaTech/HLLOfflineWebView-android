package com.lalamove.huolala.offline.webview.log;

import com.lalamove.huolala.offline.webview.OfflineWebManager;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: OfflineWebLog
 * @author: kelvin
 * @date: 7/19/21
 * @description: 日志输出
 * @history:
 */

public class OfflineWebLog {

    private static final String DEFAULT_TAG = "OffLineWeb_";

    public static void e(String tag, String content) {
        if (getLogger() == null) {
            if (OfflineWebManager.getInstance().isDebug()) {
                tag = Thread.currentThread().getName() + "-" + tag;
                android.util.Log.e(DEFAULT_TAG + tag, content);
            }
        } else {
            getLogger().e(tag, content);
        }
    }
    public static void e(String tag, Throwable t) {
        if (getLogger() == null) {
            if (OfflineWebManager.getInstance().isDebug()) {
                try {
                    tag = Thread.currentThread().getName() + "-" + tag;
                    android.util.Log.e(DEFAULT_TAG + tag, t.getMessage());
                }catch (Exception e){

                }
            }
        } else {
            getLogger().e(DEFAULT_TAG + tag, t);
        }
    }

    private static Logger getLogger() {
        return OfflineWebManager.getInstance().getLogger();
    }

    public static void d(String tag, String content) {
        if (getLogger() == null) {
            if (OfflineWebManager.getInstance().isDebug()) {
                tag = Thread.currentThread().getName() + "-" + tag;
                android.util.Log.d(DEFAULT_TAG + tag, content);
            }
        } else {
            getLogger().d(DEFAULT_TAG + tag, content);
        }
    }

    public static void i(String tag, String content) {
        if (getLogger() == null) {
            if (OfflineWebManager.getInstance().isDebug()) {
                tag = Thread.currentThread().getName() + "-" + tag;
                android.util.Log.i(DEFAULT_TAG + tag, content);
            }
        } else {
            getLogger().i(DEFAULT_TAG + tag, content);
        }
    }
}
