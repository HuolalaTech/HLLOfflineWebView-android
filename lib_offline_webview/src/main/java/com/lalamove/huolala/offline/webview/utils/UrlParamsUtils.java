package com.lalamove.huolala.offline.webview.utils;

import android.text.TextUtils;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: UrlParamsUtils
 * @author: kelvin
 * @date: 7/23/21
 * @description: url参数工具类
 * @history:
 */

public class UrlParamsUtils {

    /**
     * url后追加参数
     */
    public static String urlAppendParam(String url, String key, String value) {
        if (TextUtils.isEmpty(url)||url.contains(key)) {
            return url;
        }
        StringBuilder stringBuilder = new StringBuilder(url);
        if (url.contains("=")) {
            return stringBuilder.append("&").append(key).append("=").append(value).toString();
        } else {
            return stringBuilder.append("?").append(key).append("=").append(value).toString();
        }

    }

}
