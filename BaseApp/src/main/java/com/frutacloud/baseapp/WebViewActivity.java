package com.frutacloud.baseapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.frutacloud.baseapp.base.BaseActivity;

/**
 * Created by Administrator on 2017/11/20.
 */

public class WebViewActivity extends BaseActivity {

    private WebView wv_content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        initWebView();

    }

    private void initWebView() {

        wv_content = (WebView) findViewById(R.id.wv_content);

        WebSettings wvSet = wv_content.getSettings();
        wvSet.setJavaScriptEnabled(true);
        wvSet.setJavaScriptCanOpenWindowsAutomatically(true);

        wv_content.setWebChromeClient(new WvContentChormeClient());
        wv_content.setWebViewClient(new WvContentViewClient());

    }

    @Override
    protected void requestFail(String sign, Bundle bundle) {

    }

    @Override
    protected void requestSuccess(String sign, Bundle bundle) {

    }

    private class WvContentViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return true;
        }


        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            //可在该方法内设置一个网页请求失败时的本地页面
        }
    }

    private class WvContentChormeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }
}
