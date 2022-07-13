package com.lalamove.huolala.client.offline_web;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.lalamove.huolala.client.offline_web.widget.OffWebBottomView;
import com.lalamove.huolala.offline.webview.OfflineWebClient;
import com.lalamove.huolala.offline.webview.info.OfflinePackageInfo;
import com.lalamove.huolala.offline.webview.resource.ResourceFlow;
import com.lalamove.huolala.offline.webview.utils.OfflineHandlerUtils;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: MainActivity
 * @author: kelvin
 * @date: 2022/5/13
 * @description: demo首页
 * @history:
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.off_web_activity_main);
        init();
    }

    private void init() {
        OfflineWebInitJob offlineWebJob = new OfflineWebInitJob();
        //初始化所有自定义参数
        offlineWebJob.initAll(this);
        //只初始化必须参数
        offlineWebJob.init(this);
        initListener();
    }

    private void initListener() {
        View openEhnWebView = findViewById(R.id.open_ehn_web);
        View updateView = findViewById(R.id.update_btn);
        View openNormalView = findViewById(R.id.open_normal_btn);
        View cleanView = findViewById(R.id.clean_btn);
        updateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUpdate();
            }
        });
        openNormalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoActivity.start(MainActivity.this, "https://www.baidu.com/?offweb=act3-2108-turntable");
            }
        });
        openEhnWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnhDemoActivity.start(MainActivity.this, "https://www.baidu.com/?offweb=act3-2108-turntable");
            }
        });
        cleanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OfflineWebClient.clean();
                OffWebBottomView.show(MainActivity.this, "清理离线包", "清理成功！", null);
            }
        });
    }

    private void checkUpdate() {
        //通常我们在后台静默更新，不需要回调。
        OfflineWebClient.checkPackage("act3-2108-turntable", new ResourceFlow.FlowListener() {
            @Override
            public void done(OfflinePackageInfo packageInfo) {
                OfflineHandlerUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        OffWebBottomView.show(MainActivity.this,
                                "更新离线包",
                                "成功：",
                                packageInfo != null ? packageInfo.toString() : "null");
                    }
                });
            }

            @Override
            public void error(OfflinePackageInfo packageInfo, Throwable e) {
                OfflineHandlerUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        OffWebBottomView.show(MainActivity.this,
                                "更新离线包",
                                "失败：",
                                packageInfo != null ? packageInfo.toString() : "",
                                "失败原因：",
                                e != null ? e.toString() : "null");
                    }
                });
            }
        });
    }

}