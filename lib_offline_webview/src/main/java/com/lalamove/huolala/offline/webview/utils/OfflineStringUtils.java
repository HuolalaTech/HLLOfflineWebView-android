package com.lalamove.huolala.offline.webview.utils;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: OfflineStringUtils
 * @author: kelvin
 * @date: 8/12/21
 * @description: 字符串工具
 * @history:
 */

public class OfflineStringUtils {

    private OfflineStringUtils() {
    }

    public static String getErrorString(Throwable t) {
        if (t == null) {
            return "UnknownError";
        }
        try {
            return t.getMessage();
        } catch (Exception e) {
            return "UnknownError";
        }
    }

    public static String appendUnsafeString(String... args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String arg : args) {
            stringBuilder.append(arg);
        }
        return stringBuilder.toString();
    }
}
