package com.lalamove.huolala.offline.webview.resource;

import android.text.TextUtils;

import com.lalamove.huolala.offline.webview.OfflineWebManager;
import com.lalamove.huolala.offline.webview.info.OfflinePackageInfo;
import com.lalamove.huolala.offline.webview.log.OfflineWebLog;
import com.lalamove.huolala.offline.webview.net.RequestCallback;
import com.lalamove.huolala.offline.webview.utils.OfflineStringUtils;


/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: FetchPackageFlow
 * @author: kelvin
 * @date: 7/27/21
 * @description: 获取更新包
 * @history:
 */

public class FetchPackageFlow implements ResourceFlow.IFlow {

    private static final String TAG = FetchPackageFlow.class.getSimpleName();
    private ResourceFlow mResourceFlow;

    public FetchPackageFlow(ResourceFlow resourceFlow) {
        mResourceFlow = resourceFlow;
    }

    @Override
    public void process() throws ResourceFlow.FlowException {
        if (TextUtils.isEmpty(mResourceFlow.getPackageInfo().getBisName())) {
            mResourceFlow.getReportParams().setQueryResult(false, "bisName == null ");
            mResourceFlow.error(new IllegalStateException("FetchPackageFlow  bisName == null "));
            return;
        }
        OfflineWebManager.getInstance().getRequest().requestPackageInfo(
                mResourceFlow.getPackageInfo().getBisName(),
                mResourceFlow.getPackageInfo().getVersion(),
                new RequestCallback<OfflinePackageInfo>() {
                    @Override
                    public void onSuccess(OfflinePackageInfo offlinePackageInfo) {
                        mResourceFlow.getReportParams().queryEnd();

                        if (offlinePackageInfo == null) {
                            //如果为空，则代表响应数据有问题
                            mResourceFlow.getReportParams().setQueryResult(false, "onResponse：null");
                            mResourceFlow.setDone();
                            return;
                        }
                        OfflineWebManager.getInstance()
                                .getSharedPreferences()
                                .edit()
                                .putBoolean(offlinePackageInfo.getBisName(), offlinePackageInfo.isEnable())
                                .apply();
                        mResourceFlow.getReportParams().setQueryResult(true, offlinePackageInfo.toString());


                        if (!mResourceFlow.getPackageInfo().getBisName().equals(offlinePackageInfo.getBisName())) {
                            mResourceFlow.getReportParams().setQueryResult(false, "bizName error");
                            mResourceFlow.setDone();
                            return;
                        }
                        if (!offlinePackageInfo.isEnable()) {
                            OfflineWebLog.i(TAG, "isEnable = false");
                            mResourceFlow.setDone();
                            return;
                        }
                        if (offlinePackageInfo.isSameVer()) {
                            OfflineWebLog.i(TAG, "same version");
                            mResourceFlow.setDone();
                            return;
                        }
                        if (offlinePackageInfo.isNeedUpdate()) {
                            mResourceFlow.setPackageInfo(offlinePackageInfo);
                            mResourceFlow.process();
                            return;
                        }
                        OfflineWebLog.e(TAG, "unknown status :" + offlinePackageInfo.getResult());
                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        OfflineWebLog.e(TAG, throwable);
                        mResourceFlow.getReportParams().queryEnd();
                        mResourceFlow.getReportParams().setQueryResult(false, OfflineStringUtils.getErrorString(throwable));
                        mResourceFlow.error(throwable);
                    }
                }
        );
//        OkHttpClient okhttpClient = OfflineWebViewManager.getInstance().getOkHttpClient();
//        HttpUrl httpUrl = HttpUrl.parse(OfflineWebViewManager.getInstance().getBaseUrl());
//        if (httpUrl == null) {
//            if (OfflineWebViewManager.getInstance().isDebug()) {
//                throw new IllegalStateException("parse error url :" + OfflineWebViewManager.getInstance().getBaseUrl());
//            } else {
//                OfflineWebLog.e(TAG, new IllegalStateException("parse error url :" + OfflineWebViewManager.getInstance().getBaseUrl()));
//                return;
//            }
//        }
//        Request request = getRequest(httpUrl);
//        Call call = okhttpClient.newCall(request);
//        mResourceFlow.getReportParams().queryStart();
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                OfflineWebLog.e(TAG, e);
//                mResourceFlow.getReportParams().queryEnd();
//                mResourceFlow.getReportParams().setQueryResult(false, OfflineStringUtils.getErrorString(e));
//                mResourceFlow.error(e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                mResourceFlow.getReportParams().queryEnd();
//                parseResponse(response);
//            }
//        });
    }

//    private void parseResponse(Response response) {
//        try {
//            String responseStr = response.body().string();
//            OfflineWebLog.i(TAG, "onResponse：" + responseStr);
//            OfflinePackageInfo offlinePackageInfo = OfflineGsonUtils.fromJson(responseStr, OfflinePackageInfo.class);
//            if (offlinePackageInfo == null) {
//                //如果为空，则代表响应数据有问题
//                mResourceFlow.getReportParams().setQueryResult(false, "onResponse：" + responseStr);
//                mResourceFlow.setDone();
//                return;
//            }
//            OfflineWebViewManager.getInstance()
//                    .getSharedPreferences()
//                    .edit()
//                    .putBoolean(offlinePackageInfo.getBisName(), offlinePackageInfo.isEnable())
//                    .apply();
//            mResourceFlow.getReportParams().setQueryResult(true, responseStr);
//
//
//            if (!mResourceFlow.getPackageInfo().getBisName().equals(offlinePackageInfo.getBisName())) {
//                mResourceFlow.getReportParams().setQueryResult(false, "bizName error");
//                mResourceFlow.setDone();
//                return;
//            }
//            if (!offlinePackageInfo.isEnable()) {
//                OfflineWebLog.i(TAG, "isEnable = false");
//                mResourceFlow.setDone();
//                return;
//            }
//            if (offlinePackageInfo.isSameVer()) {
//                OfflineWebLog.i(TAG, "same version");
//                mResourceFlow.setDone();
//                return;
//            }
//            if (offlinePackageInfo.isNeedUpdate()) {
//                mResourceFlow.setPackageInfo(offlinePackageInfo);
//                mResourceFlow.process();
//                return;
//            }
//            OfflineWebLog.e(TAG, "unknown status :" + offlinePackageInfo.getResult());
//
//        } catch (Exception e) {
//            mResourceFlow.getReportParams().setQueryResult(false, OfflineStringUtils.getErrorString(e));
//            mResourceFlow.error(e);
//        }
//    }
//
//    private Request getRequest(HttpUrl httpUrl) {
//        HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
//        urlBuilder.addQueryParameter(OfflineConstant.BIS_NAME, mResourceFlow.getPackageInfo().getBisName())
//                .addQueryParameter(OfflineConstant.PARAM_OFFLINE_ZIP_VER, mResourceFlow.getPackageInfo().getVersion())
////                .addQueryParameter(OfflineConstant.PARAM_ENV, OfflineWebViewManager.getInstance().getEnv())
////                .addQueryParameter(OfflineConstant.PARAM_CLIENT_VERSION, OfflineWebViewManager.getInstance().getClientVersion())
//                .addQueryParameter(OfflineConstant.PARAM_OS, "android");
//        HttpUrl url = urlBuilder.build();
//        OfflineWebLog.i(TAG, "fetch url：" + url.toString());
//        return new Request.Builder()
//                .url(url)
//                .get()
//                .build();
//    }

}
