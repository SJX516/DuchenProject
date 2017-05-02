package com.duchen.template.usage.TestBrowser;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.duchen.template.usage.AppActivityBase;
import com.duchen.template.usage.R;
import com.tencent.smtt.sdk.WebView;

public class BrowserActivity extends AppActivityBase {

    private static final String KEY_URL = "key_url";

    private WebView mWebView;

    public static void launch(Context context, String url) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra(KEY_URL, url);
        context.startActivity(intent);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_browser);
    }

    @Override
    public void findViews() {
        mWebView = (WebView) findViewById(R.id.webview);
    }

    @Override
    public void initViews() {
        String url = getIntent().getStringExtra(KEY_URL);
        mWebView.loadUrl(url);
    }

    @Override
    public void handleClick(int id, View v) {

    }
}
