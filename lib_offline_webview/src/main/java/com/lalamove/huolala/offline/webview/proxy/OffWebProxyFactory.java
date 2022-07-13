package com.lalamove.huolala.offline.webview.proxy;

import com.lalamove.huolala.offline.webview.OfflineWebManager;
import com.lalamove.huolala.offline.webview.widget.IOfflineWebView;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: WebProxyFactory
 * @author: kelvin
 * @date: 8/17/21
 * @description: 离线包功能代理工厂
 * @history:
 */

public class OffWebProxyFactory {

    private OffWebProxyFactory() {
    }

    public static IOfflineWebViewProxy getProxy(IOfflineWebView webView) {
        if (OfflineWebManager.getInstance().isInit()) {
            return new OfflineWebViewProxy(webView);
        } else {
            return new EmptyOfflineWebViewProxy();
        }
    }
}
