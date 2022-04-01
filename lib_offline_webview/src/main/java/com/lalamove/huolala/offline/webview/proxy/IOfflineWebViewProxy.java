package com.lalamove.huolala.offline.webview.proxy;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: IOfflineWebViewProxy
 * @author: kelvin
 * @date: 8/17/21
 * @description: 离线包功能代理
 * @history:
 */

public interface IOfflineWebViewProxy {

    /**
     * 获取 业务名称
     * @return 业务名称
     */
    String getBisName();

    /**
     * 加载url
     * @param url 源url
     * @return 拼接离线包参数的url
     */
    String loadUrl(String url);

    /**
     * 调用重新加载页面，只有离线包返回强制更新才会调用。业务方根据需要实现，默认无操作
     */
    void reLoadUrl();

    /**
     * 销毁
     */
    void destroy();
}
