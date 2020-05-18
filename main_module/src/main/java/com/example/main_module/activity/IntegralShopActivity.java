package com.example.main_module.activity;


import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.baselib.base.MVPBaseActivity;
import com.example.main_module.R;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.QMUIProgressBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.webview.QMUIWebView;
import com.qmuiteam.qmui.widget.webview.QMUIWebViewClient;

public class IntegralShopActivity extends MVPBaseActivity {


    private QMUIWebView mWebView;
    private QMUIProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_integral_shop);
        initView();
        initWebView();
        initListener();
    }

    /**
     * 初始化监听
     */
    private void initListener() {

     //   mProgressBar.setQMUIProgressBarTextGenerator((progressBar, value, maxValue) -> value + "/" + maxValue);

        mWebView.setWebViewClient(new QMUIWebViewClient(true, false) {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.GONE);//消失
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
            }
        });


        mWebView.setCallback(() -> new QMUIDialog.MessageDialogBuilder(getContext())
                .setMessage("Do not support to change css env")
                .addAction(new QMUIDialogAction(getContext(), R.string.ok, (dialog, index) -> dialog.dismiss()))
                .setSkinManager(QMUISkinManager.defaultInstance(getContext()))
                .show());
    }

    /**
     * 初始化webView
     */
    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //设置缓存
        mWebView.loadUrl("http://m.7dingdong.com/userinfo/index_mark?store_id=4446139&id=12");
    }

    private void initView() {
        mWebView = findViewById(R.id.webView);
        mProgressBar = findViewById(R.id.progressBar);
    }

}
