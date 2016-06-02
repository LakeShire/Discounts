package com.github.lakeshire.discounts.fragment;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.lakeshire.discounts.R;

/**
 * Created by nali on 2016/6/2.
 */
public class WebFragment extends DBaseFragment {

    private WebView mWebView;
    private String mUrl;
    private String mTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mUrl = args.getString("extra_url");
            mTitle = args.getString("extra_title");
        }
    }

    @Override
    void initUI() {
        showAction(R.drawable.ic_search);
        showBack(true);
        setTitle(mTitle);

        mWebView = (WebView) find(R.id.webview);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.loadUrl(mUrl);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_web;
    }
}
