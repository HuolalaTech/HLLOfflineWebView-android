package com.lalamove.huolala.offline.webview.utils;

import android.text.TextUtils;

import com.lalamove.huolala.offline.webview.info.OfflineZipPackageConfig;

import java.io.File;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: OfflinePackageUtil
 * @author: kelvin
 * @date: 7/21/21
 * @description: 更新包文件 相关方法
 * @history:
 */

public class OfflinePackageUtil {

    public static OfflineZipPackageConfig getOfflineConfig(String bisName) {
        String configJsonStr = OfflineFileUtils.readFile2String(getBisDir(bisName)+File.separator+OfflineConstant.CUR_DIR_NAME
                + File.separator + OfflineConstant.CONFIG_FILE_NAME);
        if (TextUtils.isEmpty(configJsonStr)) {
            return null;
        }
        return OfflineGsonUtils.fromJson(configJsonStr, OfflineZipPackageConfig.class);
    }

    public static String getResDir() {
        return OfflineFileUtils.getInternalAppDataPath() + File.separator + OfflineConstant.ROOT_DIR_NAME;
    }

    public static String getBisDir(String bisName) {
        return getResDir() + File.separator + bisName;
    }


    public static String getPackageVersion(String bisName) {
        OfflineZipPackageConfig offlineConfig = OfflinePackageUtil.getOfflineConfig(bisName);
        if (offlineConfig == null) {
            return "0";
        }
        return offlineConfig.getVer();
    }


}
