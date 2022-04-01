package com.lalamove.huolala.offline.webview.task;

import com.lalamove.huolala.offline.webview.log.OfflineWebLog;
import com.lalamove.huolala.offline.webview.utils.OfflineConstant;
import com.lalamove.huolala.offline.webview.utils.OfflineFileUtils;
import com.lalamove.huolala.offline.webview.utils.OfflinePackageUtil;

import java.io.File;
import java.io.FileFilter;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: CheckVersionTask
 * @author: kelvin
 * @date: 8/6/21
 * @description: 初始化之后检测新旧离线包版本 进行删除替换
 * @history:
 */

public class CheckVersionTask implements Runnable {

    private static final String TAG = CheckVersionTask.class.getSimpleName();

    @Override
    public void run() {
        OfflineWebLog.i(TAG, "checkVersions");
        File file = new File(OfflinePackageUtil.getResDir());
        if (file.exists()) {
            File[] packageDirs = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory();
                }
            });
            if (packageDirs != null && packageDirs.length != 0) {
                for (File packageDir : packageDirs) {
                    OfflineWebLog.i(TAG, "check :" + packageDir.getName());
                    File nowFile = new File(packageDir, OfflineConstant.NEW_DIR_NAME);
                    File curFile = new File(packageDir, OfflineConstant.CUR_DIR_NAME);
                    if (nowFile.exists() && curFile.exists()) {
                        //存在 cur 和new
                        //cur->old
                        OfflineFileUtils.rename(curFile, OfflineConstant.OLD_DIR_NAME);
                        //new->cur
                        OfflineFileUtils.rename(nowFile, OfflineConstant.CUR_DIR_NAME);
                        OfflineWebLog.i(TAG, "update replace");
                    } else if (nowFile.exists()) {
                        OfflineWebLog.i(TAG, "update");
                        //new->cur
                        OfflineFileUtils.rename(nowFile, OfflineConstant.CUR_DIR_NAME);
                    }

                    File oldFile = new File(packageDir, OfflineConstant.OLD_DIR_NAME);

                    //删除old
                    if (oldFile.exists()) {
                        OfflineWebLog.i(TAG, "del start");
                        OfflineFileUtils.deleteDir(oldFile);
                        OfflineWebLog.i(TAG, "del done");
                    }
                }
            }
        }
    }
}
