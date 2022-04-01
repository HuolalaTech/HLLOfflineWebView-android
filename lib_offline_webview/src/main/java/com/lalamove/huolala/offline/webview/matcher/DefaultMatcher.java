package com.lalamove.huolala.offline.webview.matcher;

import android.net.Uri;

import com.lalamove.huolala.offline.webview.OfflineWebManager;
import com.lalamove.huolala.offline.webview.log.OfflineWebLog;
import com.lalamove.huolala.offline.webview.utils.OfflineConstant;
import com.lalamove.huolala.offline.webview.utils.OfflineFileUtils;
import com.lalamove.huolala.offline.webview.utils.OfflinePackageUtil;
import com.lalamove.huolala.offline.webview.utils.UrlParamsUtils;

import java.io.File;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: DefaultMatcher
 * @author: kelvin
 * @date: 7/20/21
 * @description: 业务匹配器
 * @history:
 */

public class DefaultMatcher implements BisNameMatcher {

    private static final String TAG = DefaultMatcher.class.getSimpleName();

    public DefaultMatcher() {
    }

    @Override
    public String matching(String url) {
        String bisName = null;
        Uri parse = null;
        try {
            parse = Uri.parse(url);
            bisName = parse.getQueryParameter(OfflineConstant.OFF_WEB);
        } catch (Exception e) {
            OfflineWebLog.e(TAG, e);
        }

        if (parse == null || OfflineFileUtils.isSpaceString(bisName)) {
            return url;
        }

        if (!OfflineWebManager.getInstance().getSharedPreferences().getBoolean(bisName, true)) {
            OfflineWebLog.i(TAG, "match url :" + bisName + " is disabled by sp");
            return url;
        }

        if (OfflineWebManager.getInstance().getOfflineConfig().isDisable(bisName)) {
            OfflineWebLog.i(TAG, "match url :" + bisName + " is disabled by user");
            return url;
        }

        String bisDir = OfflinePackageUtil.getBisDir(bisName);
        File file = new File(bisDir + File.separator + OfflineConstant.CUR_DIR_NAME + File.separator + OfflineConstant.HTML_FILE);
        if (file.exists()) {
            OfflineWebLog.d(TAG, file.getAbsolutePath());
            String params = url.substring(url.indexOf("?"));
            if (params.contains("#")) {
                String beforeParams = parse.getQuery();
                String afterParams = parse.getFragment();
                beforeParams = UrlParamsUtils.urlAppendParam(beforeParams, OfflineConstant.PARAM_OFFWEB_HOST, parse.getHost());
                if (!"/".equals(afterParams)) {
                    afterParams = UrlParamsUtils.urlAppendParam(afterParams, OfflineConstant.PARAM_OFFWEB_HOST, parse.getHost());
                }
                return "file://" + file.getAbsolutePath() + "?" + beforeParams + "#" + afterParams;
            } else {
                params = UrlParamsUtils.urlAppendParam(params, OfflineConstant.PARAM_OFFWEB_HOST, parse.getHost());
                return "file://" + file.getAbsolutePath() + params;
            }
        } else {
            OfflineWebLog.d(TAG, "file not found : " + file.getAbsolutePath());
            return url;
        }
    }
}
