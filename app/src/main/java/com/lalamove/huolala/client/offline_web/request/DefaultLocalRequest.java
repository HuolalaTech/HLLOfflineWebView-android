package com.lalamove.huolala.client.offline_web.request;

import android.util.Log;

import com.lalamove.huolala.client.offline_web.Constants;
import com.lalamove.huolala.client.offline_web.util.OkHttpClientManager;
import com.lalamove.huolala.offline.webview.OfflineWebManager;
import com.lalamove.huolala.offline.webview.info.OfflinePackageInfo;
import com.lalamove.huolala.offline.webview.log.OfflineWebLog;
import com.lalamove.huolala.offline.webview.net.IOfflineRequest;
import com.lalamove.huolala.offline.webview.net.RequestCallback;
import com.lalamove.huolala.offline.webview.utils.OfflineConstant;
import com.lalamove.huolala.offline.webview.utils.OfflineGsonUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: DefaultRequest
 * @author: kelvin
 * @date: 3/29/22
 * @description: 离线包内部网络请求
 * @history:
 */

public class DefaultLocalRequest implements IOfflineRequest {

    private String TAG = DefaultLocalRequest.class.getSimpleName();

    /**
     * 拉取离线包信息
     * @param bizName  当前离线包业务名称
     * @param version  当前离线包版本
     * @param callback 回调
     */
    @Override
    public void requestPackageInfo(String bizName, String version, RequestCallback<OfflinePackageInfo> callback) {
        OkHttpClient okhttpClient = OkHttpClientManager.getUnsafeOkHttpClient();
        HttpUrl httpUrl = HttpUrl.parse(Constants.LOCAL_BASE_URL);
        if (httpUrl == null) {
            if (OfflineWebManager.getInstance().isDebug()) {
                throw new IllegalStateException("parse error url :" + Constants.LOCAL_BASE_URL);
            } else {
                OfflineWebLog.e(TAG, new IllegalStateException("parse error url :" + Constants.LOCAL_BASE_URL));
                return;
            }
        }
        if (callback == null) {
            return;
        }
        Request request = getRequest(httpUrl, bizName, version);
        Call call = okhttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFail(e);
                Log.e(TAG, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.e(TAG, responseStr);
                OfflinePackageInfo offlinePackageInfo = OfflineGsonUtils.fromJson(responseStr, OfflinePackageInfo.class);
                callback.onSuccess(offlinePackageInfo);
            }
        });
    }

    private Request getRequest(HttpUrl httpUrl, String bizName, String version) {
        HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
        urlBuilder.addQueryParameter(OfflineConstant.BIS_NAME, bizName)
                .addQueryParameter(OfflineConstant.PARAM_OFFLINE_ZIP_VER, version);
        HttpUrl url = urlBuilder.build();
        OfflineWebLog.i(TAG, "fetch url：" + url.toString());
        return new Request.Builder()
                .url(url)
                .get()
                .build();
    }
}
