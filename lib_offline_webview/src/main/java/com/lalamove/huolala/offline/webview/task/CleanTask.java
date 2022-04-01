package com.lalamove.huolala.offline.webview.task;

import com.lalamove.huolala.offline.webview.log.OfflineWebLog;
import com.lalamove.huolala.offline.webview.utils.OfflineFileUtils;
import com.lalamove.huolala.offline.webview.utils.OfflinePackageUtil;

import java.io.File;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: CleanTask
 * @author: kelvin
 * @date: 3/30/22
 * @description: 清理所有离线包
 * @history:
 */

public class CleanTask implements Runnable {
    private static final String TAG = CleanTask.class.getSimpleName();

    @Override
    public void run() {
        try {
            OfflineFileUtils.deleteDir(new File(OfflinePackageUtil.getResDir()));
        } catch (Exception e) {
            OfflineWebLog.e(TAG, e);
        }
    }
}
