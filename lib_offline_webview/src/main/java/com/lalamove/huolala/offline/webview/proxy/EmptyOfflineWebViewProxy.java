package com.lalamove.huolala.offline.webview.proxy;


/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: EmptyOfflineWebViewProxy
 * @author: kelvin
 * @date: 8/17/21
 * @description: 为初始化是返回空代理
 * @history:
 */

public class EmptyOfflineWebViewProxy implements IOfflineWebViewProxy{
    @Override
    public String getBisName() {
        return "";
    }

    @Override
    public String loadUrl(String url) {
        return url;
    }

    @Override
    public void reLoadUrl() {

    }

    @Override
    public void destroy() {

    }
}
