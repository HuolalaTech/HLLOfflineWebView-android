package com.lalamove.huolala.offline.webview.widget;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: ReloadOfflineWebView
 * @author: kelvin
 * @date: 7/27/21
 * @description: 使用端 实现接口  支持reload
 * @history:
 */

public interface ReloadOfflineWebView extends IOfflineWebView {
    /**
     * 重载webview ，只有下发强制重载才会执行，默认无操作，业务方自己实现
     */
    void reloadOfflineWeb();
}
