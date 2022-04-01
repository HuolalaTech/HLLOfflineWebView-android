package com.lalamove.huolala.offline.webview.net;

import com.lalamove.huolala.offline.webview.info.OfflinePackageInfo;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: IOfflineRequest
 * @author: kelvin
 * @date: 3/29/22
 * @description: 网络请求
 * @history:
 */

public interface IOfflineRequest {

    /**
     * 请求离线包数据
     *
     * @param bizName  当前离线包业务名称
     * @param version  当前离线包版本
     * @param callback 回调
     */
    void requestPackageInfo(String bizName, String version, RequestCallback<OfflinePackageInfo> callback);
}
