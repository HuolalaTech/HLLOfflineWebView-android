package com.lalamove.huolala.offline.webview.utils;

import android.os.Handler;
import android.os.Looper;

import com.lalamove.huolala.offline.webview.OfflineWebManager;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: OfflineHandlerUtils
 * @author: kelvin
 * @date: 7/19/21
 * @description: 离线包 handler
 * @history:
 */
public class OfflineHandlerUtils {
    private static final String TAG = OfflineHandlerUtils.class.getSimpleName();

    private static Handler sHandler;

    private OfflineHandlerUtils() {
    }

    public static void runOnUiThread(Runnable action) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            if (sHandler == null) {
                sHandler = new Handler(Looper.getMainLooper());
            }
            sHandler.post(action);
        } else {
            try {
                action.run();
            } catch (Exception e) {
                OfflineWebManager.getInstance().getLogger().e(TAG, e);
            }
        }
    }

    public static void post(Runnable action) {
        if (sHandler == null) {
            sHandler = new Handler(Looper.getMainLooper());
        }
        sHandler.post(action);
    }

    public static void postDelayed(Runnable action, long delayMillis) {
        if (sHandler == null) {
            sHandler = new Handler(Looper.getMainLooper());
        }
        sHandler.postDelayed(action, delayMillis);
    }
}
