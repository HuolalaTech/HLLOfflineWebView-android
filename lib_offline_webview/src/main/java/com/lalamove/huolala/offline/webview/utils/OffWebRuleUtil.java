package com.lalamove.huolala.offline.webview.utils;

import android.net.Uri;
import android.text.TextUtils;

import com.lalamove.huolala.offline.webview.info.OfflineRuleConfig;

import java.util.List;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: OfflineRuleConfig
 * @author: kelvin
 * @date: 1/18/22
 * @description: 根据下发的离线包配置 添加离线包参数
 * @history:
 */

public class OffWebRuleUtil {

    private OffWebRuleUtil() {
    }

    private static final String OFF_WEB_KEY = "offweb";

    private static OfflineRuleConfig sOfflineWebConfig;

    private static List<OfflineRuleConfig.RulesInfo> getRules(){
        if (sOfflineWebConfig == null) {
            return null;
        }
       return sOfflineWebConfig.getRules();
    }

    public static void init(OfflineRuleConfig offlineRuleConfig) {
        sOfflineWebConfig = offlineRuleConfig;
    }

    /**
     * case:参数替换 xiaomi8 1000次小于113ms
     *
     * @param url 链接
     * @return 根据规则拼接离线包参数的链接
     */

    public static String addOfflineParam(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        //无规则数据
        if (getRules() == null || getRules().size() == 0) {
            return url;
        }

        try {
            //解析host，path，fragment
            Uri uri = Uri.parse(url);
            // host
            String host = uri.getHost();

            // path
            String path = uri.getPath();
            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }
            //链接不合法
            if (TextUtils.isEmpty(path) || TextUtils.isEmpty(host)) {
                return url;
            }

            //截取不带参数的fragment
            String fragment = uri.getFragment();
            if (!TextUtils.isEmpty(fragment)) {
                if (fragment.contains("?")) {
                    // /cd-index
                    fragment = fragment.substring(0, fragment.indexOf("?"));
                }
            }
            //获取匹配的离线包参数
            String params = matchRule(host, path, fragment);
            // path = "uapp" "fragment = /cd-index"
            if (TextUtils.isEmpty(params)) {
                return url;
            }
            return addParams(url, uri, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String addParams(String carUrl, Uri carUri, String params) {
        int preUrlndex = carUrl.indexOf("#");
        String beforeHash;
        String afterHash;
        if (preUrlndex == -1) {
            beforeHash = carUrl;
            afterHash = "";
        } else {
            beforeHash = carUrl.substring(0, preUrlndex);
            afterHash = carUrl.substring(preUrlndex);
        }
        if (beforeHash.endsWith("/")) {
            beforeHash = beforeHash.substring(0, beforeHash.length() - 1);
        }

        //不含有离线包参数 直接添加
        if (TextUtils.isEmpty(carUri.getQueryParameter(OFF_WEB_KEY))) {
            //没有offweb参数
            return appendParams(params, beforeHash) + afterHash;
        } else {
            //已经有offweb参数 修改替换
            return replaceParams(carUrl, carUri, params, beforeHash, afterHash);
        }
    }

    private static String replaceParams(String carUrl, Uri carUri, String params, String beforeHash, String afterHash) {
        int paramsStartIndex = beforeHash.indexOf("?");
        if (paramsStartIndex <= 0) {
            return carUrl;
        }
        String substring = beforeHash.substring(0, paramsStartIndex);
        StringBuilder stringBuilder = new StringBuilder(substring);
        boolean appended = false;
        for (String queryParameterName : carUri.getQueryParameterNames()) {
            stringBuilder.append(appended ? "&" : "?");
            appended = true;
            if (OFF_WEB_KEY.equals(queryParameterName)) {
                stringBuilder.append(OFF_WEB_KEY).append("=").append(params);
            } else {
                stringBuilder.append(queryParameterName).append("=").append(carUri.getQueryParameter(queryParameterName));
            }
        }
        return stringBuilder + afterHash;
    }

    private static String appendParams(String params, String beforeHash) {
        StringBuilder urlBuilder = new StringBuilder(beforeHash);
        if (beforeHash.contains("?")) {
            urlBuilder.append("&");
        } else {
            urlBuilder.append("?");
        }
        urlBuilder.append(OFF_WEB_KEY).append("=").append(params);
        return urlBuilder.toString();
    }

    /**
     * 匹配离线包
     */
    private static String matchRule(String host, String path, String fragment) {
        List<OfflineRuleConfig.RulesInfo> rulesList = getRules();
        if (rulesList == null || rulesList.size() == 0) {
            return null;
        }
        for (OfflineRuleConfig.RulesInfo rulesInfo : rulesList) {
            if (TextUtils.isEmpty(rulesInfo.getOffWeb())) {
                continue;
            }
            //host 不匹配
            if (rulesInfo.getHost() == null || !rulesInfo.getHost().contains(host)) {
                continue;
            }
            //path 无下发path匹配
            if (rulesInfo.getPath() == null || rulesInfo.getPath().size() == 0) {
                continue;
            }
            boolean matchPath = isMatchPath(path, rulesInfo);
            if (!matchPath) {
                continue;
            }
            //下发fragment规则为空 不做限制 匹配成功
            if (rulesInfo.getFragmentPrefix() == null || rulesInfo.getFragmentPrefix().size() == 0) {
                return rulesInfo.getOffWeb();
            }
            for (String fragmentRule : rulesInfo.getFragmentPrefix()) {
                if (TextUtils.isEmpty(fragment) && TextUtils.isEmpty(fragmentRule)) {
                    return rulesInfo.getOffWeb();
                }
                if (fragmentRule != null && fragmentRule.equals(fragment)) {
                    return rulesInfo.getOffWeb();
                }
            }
        }
        return null;
    }

    private static boolean isMatchPath(String path, OfflineRuleConfig.RulesInfo rulesInfo) {
        boolean matchPath = false;
        for (String rulePath : rulesInfo.getPath()) {
            if (TextUtils.isEmpty(rulePath)) {
                continue;
            }
            //匹配成功跳出循环
            if (path.equals(rulePath)) {
                matchPath = true;
                break;
            }
            //匹配不成功，不含* ，进入下一个path匹配
            if (!rulePath.contains("*")) {
                matchPath = false;
                continue;
            }
            String[] ruleSplit = trimPath(rulePath).split("/");
            String[] curSplit = trimPath(path).split("/");
            if (ruleSplit.length == curSplit.length) {
                boolean isSegmentMatch = true;
                for (int i = 0; i < ruleSplit.length; i++) {
                    //星号 跳过匹配
                    if ("*".equals(ruleSplit[i])) {
                        continue;
                    }
                    if (TextUtils.isEmpty(ruleSplit[i]) || TextUtils.isEmpty(curSplit[i])) {
                        isSegmentMatch = false;
                        break;
                    }
                    if (!ruleSplit[i].equals(curSplit[i])) {
                        isSegmentMatch = false;
                        break;
                    }
                }
                if (isSegmentMatch) {
                    matchPath = true;
                    break;
                }
            }
        }
        return matchPath;
    }

    private static String trimPath(String path) {
        String curPath = path;
        //清除前后"/"
        if (curPath.startsWith("/")) {
            curPath = curPath.substring(1);
        }
        if (curPath.endsWith("/")) {
            curPath = curPath.substring(0, curPath.length() - 1);
        }
        return curPath;
    }
}