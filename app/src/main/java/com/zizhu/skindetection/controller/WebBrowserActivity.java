package com.zizhu.skindetection.controller;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zizhu.skindetection.R;
import com.zizhu.skindetection.base.activity.BaseActivity;

/**
 * 浏览器
 */
public class WebBrowserActivity extends BaseActivity {

    public static final String URL_KEY = "url";
    private String mUrl = "";// 当前访问的页面的url
    private WebView webView;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_web_browser;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        webView = (WebView) this.findViewById(R.id.activity_browser_webview);
    }

    @Override
    protected void bindEven() {
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
            }

            public void onReceivedIcon(WebView view, Bitmap icon) {
            }

            public void onReceivedTitle(WebView view, String title) {
                titleBarView.initCenterTitle(title);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            public void onPageFinished(WebView view, String url) {
            }
        });
    }

    @Override
    protected void setView() {
        mUrl = getIntent().getStringExtra(URL_KEY);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setSupportZoom(false);
        webView.loadUrl(mUrl);
    }

    @Override
    protected void onClickView(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
    }
}
