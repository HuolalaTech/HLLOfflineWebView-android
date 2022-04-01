package com.lalamove.huolala.client.offline_web;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lalamove.huolala.offline.webview.OfflineWebClient;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Switch mSwitchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        OfflineWebInitJob offlineWebJob = new OfflineWebInitJob();
        offlineWebJob.initAll(this);

        mSwitchView = findViewById(R.id.switch_view);
        mSwitchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(buttonView.getContext(), isChecked ? "打开监控功能" : "关闭监控功能", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.start1_web).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSwitchView.isChecked()) {
                    EnhDemoActivity.start(MainActivity.this, "http://www.baidu.com");
                } else {
                    DemoActivity.start(MainActivity.this, "http://www.baidu.com");
                }

            }
        });
        findViewById(R.id.start2_web).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSwitchView.isChecked()) {
                    EnhDemoActivity.start(MainActivity.this, "https://www.huolala.cn/?_token=f96c865798204ce4abc259c731c4a735&token=f96c865798204ce4abc259c731c4a735&city_id=1002&version=6592&os_type=android&brand=Redmi&model=M2012K11AC&device_id=02:00:00:00:00:00&android_id=752b6078b147d464&imei=&oaid=8a9f4b655be68404&user_fid=N2vAAzVE&portType=hlluser&user_id=N2vAAzVE");
                } else {
                    DemoActivity.start(MainActivity.this, "https://www.huolala.cn/?_token=f96c865798204ce4abc259c731c4a735&token=f96c865798204ce4abc259c731c4a735&city_id=1002&version=6592&os_type=android&brand=Redmi&model=M2012K11AC&device_id=02:00:00:00:00:00&android_id=752b6078b147d464&imei=&oaid=8a9f4b655be68404&user_fid=N2vAAzVE&portType=hlluser&user_id=N2vAAzVE");
                }

            }
        });
        findViewById(R.id.start3_web).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSwitchView.isChecked()) {
                    EnhDemoActivity.start(MainActivity.this, "https://act2-stg.huolala.cn/van/act3-turntable-x?offweb=act3-2108-turntable&offweb_host=act2-stg.huolala.cn#/");
                } else {
                    DemoActivity.start(MainActivity.this, "https://act2-stg.huolala.cn/van/act3-turntable-x?offweb=act3-2108-turntable&offweb_host=act2-stg.huolala.cn#/");
                }
            }
        });
        findViewById(R.id.start4_web).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSwitchView.isChecked()) {
                    EnhDemoActivity.start(MainActivity.this, "http://uappweb.huolala.cn/uapp/?offweb=uappweb-offline#/cd-index?city_id=1017&order_vehicle_id=50");
                } else {
                    DemoActivity.start(MainActivity.this, "http://uappweb.huolala.cn/uapp/?offweb=uappweb-offline#/cd-index?city_id=1017&order_vehicle_id=50");
                }
            }
        });
        findViewById(R.id.clean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OfflineWebClient.clean();
                clearCache(v.getContext());
                Toast.makeText(v.getContext(), "清理任务已执行", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.clean_cache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCache(v.getContext());
                Toast.makeText(v.getContext(), "清理任务已执行", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OfflineWebClient.checkPackage("act3-2108-turntable");
            }
        });
    }


    /**
     * 清除缓存
     *
     * @param context 上下文
     */
    public static void clearCache(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 清除cookie
                CookieManager.getInstance().removeAllCookies(null);
            } else {
                CookieSyncManager.createInstance(context);
                CookieManager.getInstance().removeAllCookie();
                CookieSyncManager.getInstance().sync();
            }

            new WebView(context).clearCache(true);

            File cacheFile = new File(context.getCacheDir().getParent() + "/app_webview");
            clearCacheFolder(cacheFile, System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int clearCacheFolder(File dir, long time) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, time);
                    }
                    if (child.lastModified() < time) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

}