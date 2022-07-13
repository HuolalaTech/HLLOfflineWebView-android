package com.lalamove.huolala.client.offline_web;

import android.content.Context;
import android.util.Log;

import com.lalamove.huolala.client.offline_web.request.DefaultLocalRequest;
import com.lalamove.huolala.client.offline_web.util.IoThreadPool;
import com.lalamove.huolala.offline.webview.Interceptor.IInterceptor;
import com.lalamove.huolala.offline.webview.OfflineConfig;
import com.lalamove.huolala.offline.webview.OfflineParams;
import com.lalamove.huolala.offline.webview.OfflineWebClient;
import com.lalamove.huolala.offline.webview.download.IDownLoader;
import com.lalamove.huolala.offline.webview.flow.FlowReportParams;
import com.lalamove.huolala.offline.webview.flow.IFlowResultHandleStrategy;
import com.lalamove.huolala.offline.webview.info.OfflineRuleConfig;
import com.lalamove.huolala.offline.webview.log.Logger;
import com.lalamove.huolala.offline.webview.matcher.DefaultMatcher;
import com.lalamove.huolala.offline.webview.monitor.OfflineWebMonitorUtils;
import com.lalamove.huolala.offline.webview.monitor.base.IEnhWebMonitor;
import com.lalamove.huolala.offline.webview.threadpool.IExecutorServiceProvider;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: OfflineJob
 * @author: kelvin
 * @date: 7/30/21
 * @description: 离线包启动
 * @history:
 */

public class OfflineWebInitJob {

    private static final String TAG = OfflineWebInitJob.class.getSimpleName();

    /**
     * 简单初始化，无监控功能
     *
     * @param context
     */
    public void init(Context context) {
        OfflineConfig offlineConfig = new OfflineConfig.Builder(true)
//                .addDisable("act3-2108-turntable")//禁用业务名称
                .addPreDownload("uappweb-offline")//预加载业务名称
                .build();
        OfflineWebClient.init(context.getApplicationContext(),
                new OfflineParams()
                        .config(offlineConfig)//必须
                        .requestServer(new DefaultLocalRequest())//必须
//                        .requestServer(new DefaultRequest())
                        .isDebug(BuildConfig.DEBUG)
        );
    }

    /**
     * 包含所有功能，可选初始化功能
     *
     * @param context
     */
    public void initAll(Context context) {
        OfflineConfig offlineConfig = new OfflineConfig.Builder(true)
//                .addDisable("act3-2108-turntable")//禁用业务名称
                .addPreDownload("uappweb-offline")//预加载业务名称
                .build();
        //匹配规则相关 可选
        ArrayList<String> host = new ArrayList<>();
        ArrayList<String> path = new ArrayList<>();
        ArrayList<String> fragment = new ArrayList<>();
        host.add("www.baidu.com");
        path.add("/uapp");
        fragment.add("/ccc=ddd");

        OfflineRuleConfig offlineRuleConfig = new OfflineRuleConfig();
        offlineRuleConfig.addRule(new OfflineRuleConfig.RulesInfo("act3-2108-turntable",host,path,fragment));
//        OfflineRuleConfig offlineRuleConfig = GsonUtils.fromJson(Constants.RULE_CONFIG,OfflineRuleConfig.class);
        //匹配规则相关 可选

        OfflineWebClient.init(context.getApplicationContext(),
                new OfflineParams()
                        //必须
                        .config(offlineConfig)
                        //必须
                        .requestServer(new DefaultLocalRequest())
//                        .requestServer(new DefaultRequest())
                        .isDebug(BuildConfig.DEBUG)
                        //可选 下载器
                        .downloader(new IDownLoader() {
                            @Override
                            public void download(String url, String phoneDir, String fileName, DownloadCallback callback) {
                                doDownloadBySDK(url, phoneDir, fileName, callback);
                            }
                        })
                        .addRule("act3-2108-turntable",host,path,fragment)
//                        .setRule(Constants.RULE_CONFIG)//json形式的规则
//                        .setRule(offlineRuleConfig)
                        //可选 日志打印
                        .logger(new Logger() {
                            @Override
                            public void e(String tag, String content) {
                                Log.e(tag, " " + content);
                            }

                            @Override
                            public void e(String tag, Throwable throwable) {
                                Log.e(tag, " " + throwable.getMessage());
                            }

                            @Override
                            public void d(String tag, String content) {
                                Log.d(tag, " " + content);
                            }

                            @Override
                            public void i(String tag, String content) {
                                Log.d(tag, " " + content);
                            }
                        })
                        //可选 匹配器
                        .matcher(new DefaultMatcher())
                        //可选 线程池
                        .executorServiceProvider(new IExecutorServiceProvider() {
                            @Override
                            public ExecutorService get() {
                                return IoThreadPool.getInstance().getThreadPoolExecutor();
                            }
                        })
                        //可选 监控
                        .monitor(new IEnhWebMonitor() {
                            @Override
                            public void report(String event, HashMap<String, Object> params) {
                                Log.i(TAG, "report " + event + ",param:" + params);
                            }

                            @Override
                            public void monitorSummary(String name, float value, HashMap<String, Object> labelMap, String extra) {
                                Log.i(TAG, "monitorSummary " + name + "，loadTime：" + value + ",param:" + labelMap);
                            }
                        })
                        //可选 拦截器
                        .interceptor(new IInterceptor() {
                            @Override
                            public boolean isIntercept(String bisName) {
                                return false;
                            }
                        })
                        //可选 获取更新包流程结束回调 实现之后
                        .flowResultHandleStrategy(new IFlowResultHandleStrategy() {
                            @Override
                            public void done(FlowReportParams params) {
                                OfflineWebMonitorUtils.reportFlowParams(params);
                            }
                        })

        );
    }

    private void doDownloadBySDK(String url, String phoneDir, String fileName, IDownLoader.DownloadCallback callback) {
        FileDownLoadHelper fileDownLoadHelper = new FileDownLoadHelper();
        fileDownLoadHelper.httpGetFile(url, phoneDir, fileName, new FileDownLoadHelper.OnHttpGetFileListener() {
            @Override
            public void onDownloaded(File file) {
                if (callback != null) {
                    callback.success(file, false);
                }
            }

            @Override
            public void onDownloading(int total, int percentage) {
            }
        });
    }


}
