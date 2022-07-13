package com.lalamove.huolala.offline.webview.utils;

import android.net.Uri;

import com.lalamove.huolala.offline.webview.log.OfflineWebLog;


/**
 * @author yz
 * @time 2021/12/22 3:38 下午
 */
public class EnhWebUriUtils {

    private static final String TAG = "EnhWebUriUtils";

    public static String getScheme(String urlPath) {
        try {
            return Uri.parse(urlPath).getScheme();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取监控需要的url
     * https://aaaa.cn/path?offweb=bisName#/fragment?paramA=1&paramsB=2
     * -> https://aaaa.cn/path#/fragment
     *
     * @param urlPath
     * @return
     */
    public static String getMonitorUrl(String urlPath) {
        try {
            int pathIndex = urlPath.indexOf("?");
            if (pathIndex != -1) {
                StringBuilder urlBuild = new StringBuilder();
                urlBuild.append(urlPath.substring(0, pathIndex));

                int jinIndex = urlPath.indexOf("#");
                if (jinIndex != -1) {
                    //截取后边的字符串
                    urlPath = urlPath.substring(jinIndex);
                    if (urlPath.contains("?")) {
                        //截取#跟？ 之间的字符串
                        String jinAndWen = urlPath.substring(0, urlPath.indexOf("?"));
                        //过滤当前只有一个#号字符串
                        if (!"#".equals(jinAndWen.trim())) {
                            urlBuild.append(jinAndWen);
                        }

                        OfflineWebLog.d(TAG, "monitorUrl -> " + urlBuild);
                    }
                }
                return urlBuild.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urlPath == null ? "" : urlPath;
    }
}
