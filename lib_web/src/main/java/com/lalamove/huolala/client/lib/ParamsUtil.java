package com.lalamove.huolala.client.lib;

import com.google.common.base.Splitter;

import java.util.Map;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: ParamsUtil
 * @author: kelvin
 * @date: 3/30/22
 * @description: 参数拼接工具类
 * @history:
 */

public class ParamsUtil {


    public static Map<String, String> getParamMap(String url) {
        if (isEmpty(url)) {
            return null;
        }
        try {
            String params = url.substring(url.indexOf("?") + 1);
            return Splitter.on("&")
                    .withKeyValueSeparator("=")
                    .split(params);
        } catch (Exception e) {
            return null;
        }

    }

    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
