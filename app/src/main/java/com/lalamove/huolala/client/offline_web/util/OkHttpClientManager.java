package com.lalamove.huolala.client.offline_web.util;

import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: CpuThreadPool
 * @author: muye
 * @date: 2021/7/7
 * @description: OkHttpClient实例管理
 * @history:
 */
public class OkHttpClientManager {


    private OkHttpClientManager() {
    }

    /**
     * Glide资源下载的OkHttpClient
     *
     * @return
     */
    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.dispatcher(new Dispatcher(IoThreadPool.getInstance().getThreadPoolExecutor()));
            builder.sslSocketFactory(HttpsUtils.getCustomSocketFactory(), new HttpsUtils.CustomX509TrustManager());
            builder.hostnameVerifier(HttpsUtils.getAndroidHostnameVerifier());
            builder.connectTimeout(20, TimeUnit.SECONDS);
            builder.readTimeout(20, TimeUnit.SECONDS);
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
