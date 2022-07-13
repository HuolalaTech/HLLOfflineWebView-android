package com.lalamove.huolala.offline.webview;


import com.lalamove.huolala.offline.webview.log.OfflineWebLog;
import com.lalamove.huolala.offline.webview.proxy.OfflineWebViewProxy;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: OfflinePageManager
 * @author: kelvin
 * @date: 7/19/21
 * @description: 页面管理器 用于reload
 * @history:
 */

public class OfflinePageManager {
    private static final String TAG = OfflinePageManager.class.getSimpleName();
    private List<OfflineWebViewProxy> mLinkedList = Collections.synchronizedList(new LinkedList<>());

    public OfflinePageManager() {
    }

    public void addPage(OfflineWebViewProxy webView) {
        if (mLinkedList.contains(webView)) {
            return;
        }
        mLinkedList.add(webView);
        OfflineWebLog.i(TAG, "add " + mLinkedList.size());
    }

    public void remove(OfflineWebViewProxy webView) {
        mLinkedList.remove(webView);
        OfflineWebLog.i(TAG, "remove " + mLinkedList.size());
    }

    public void reload(String bizName) {
        for (OfflineWebViewProxy webViewProxy : mLinkedList) {
            if (webViewProxy.getBisName().equals(bizName)) {
                webViewProxy.reLoadUrl();
            }
        }
    }

}
