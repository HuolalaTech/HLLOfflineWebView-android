package com.lalamove.huolala.client.offline_web;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.lalamove.huolala.offline.webview.widget.EnhOfflineWebView;
import com.lalamove.huolala.offline.webview.widget.OfflineWebView;

public class DemoActivity extends AppCompatActivity {


    public static void start(Activity activity, String url) {
        Intent intent = new Intent(activity, DemoActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", "demo_title");
        intent.putExtra("token", "");
        intent.putExtra("deviceid", "demo_id");
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        FrameLayout webContainer = findViewById(R.id.web_container);
        OfflineWebView webView = new OfflineWebView(this);
        webContainer.addView(webView,new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initWebViewSettings(webView);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });

        if (getIntent() != null) {
            String url = getIntent().getStringExtra("url");
            webView.loadUrl(url);
        }
    }
    public void initWebViewSettings(WebView webView) {
        webView.setClickable(true);
        webView.setOnTouchListener((v, event) -> {
            return false;
        });
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setAllowContentAccess(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setSupportZoom(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(9223372036854775807L);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setBlockNetworkImage(false);
        webSetting.setMixedContentMode(0);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setSupportMultipleWindows(false);
    }
}