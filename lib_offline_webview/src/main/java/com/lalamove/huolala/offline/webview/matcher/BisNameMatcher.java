package com.lalamove.huolala.offline.webview.matcher;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: BisNameMatcher
 * @author: kelvin
 * @date: 7/20/21
 * @description: 匹配器
 * @history:
 */

public interface BisNameMatcher {


    /**
     * 匹配url
     * @param url 源url
     * @return 返回匹配拼接离线包参数的链接
     */
    String matching(String url);
}
