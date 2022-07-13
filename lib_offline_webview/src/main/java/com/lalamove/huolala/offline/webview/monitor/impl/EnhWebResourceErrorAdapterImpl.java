package com.lalamove.huolala.offline.webview.monitor.impl;

import android.os.Build;
import android.webkit.WebResourceError;

import com.lalamove.huolala.offline.webview.monitor.base.IEnhWebResourceErrorAdapter;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: EnhWebResourceErrorAdapterImpl
 * @author: kelvin
 * @date: 3/28/22
 * @description:
 * @history:
 */

public class EnhWebResourceErrorAdapterImpl extends IEnhWebResourceErrorAdapter {

    private WebResourceError webkitError;
    private WebResourceError smttError;

    public EnhWebResourceErrorAdapterImpl(WebResourceError webkitError, WebResourceError smttError) {
        this.webkitError = webkitError;
        this.smttError = smttError;
    }

    @Override
    public int getErrorCode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (webkitError != null) {
                return webkitError.getErrorCode();
            }
            if (smttError != null) {
                return smttError.getErrorCode();
            }
        }
        return -1;
    }

    @Override
    public String getDescription() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (webkitError != null) {
                return webkitError.getDescription().toString();
            }
            if (smttError != null) {
                return smttError.getDescription().toString();
            }
        }

        return null;
    }
}
