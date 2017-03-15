package com.duchen.template.usage.TestBrowser;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.duchen.template.usage.AppActivityBase;
import com.duchen.template.usage.R;
import com.tencent.smtt.sdk.WebView;

public class ActivityBrowser extends AppActivityBase {

    private static final String KEY_URL = "key_url";

    private WebView mWebView;

    public static void launch(Context context, String url) {
        Intent intent = new Intent(context, ActivityBrowser.class);
        intent.putExtra(KEY_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        mWebView = (WebView) findViewById(R.id.webview);
        String url = getIntent().getStringExtra(KEY_URL);
        mWebView.loadUrl(url);
    }

    @Override
    public void handleClick(int id, View v) {

    }
}
