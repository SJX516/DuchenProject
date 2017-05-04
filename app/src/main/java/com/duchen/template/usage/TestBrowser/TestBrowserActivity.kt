package com.duchen.template.usage.TestBrowser

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import com.duchen.template.usage.AppActivityBase
import com.duchen.template.usage.R
import org.jetbrains.anko.find

class TestBrowserActivity : AppActivityBase() {

    var mEditText : EditText? = null
    var mLaunchBtn : Button? = null
    var mWebView : WebView? = null

    override fun setContentView() {
        setContentView(R.layout.activity_test_browser)
    }

    override fun findViews() {
        mEditText = find(R.id.edit_input)
        mLaunchBtn = find(R.id.btn_launch)
        mWebView = find(R.id.webview_main)
    }

    override fun initViews() {
        (mLaunchBtn as Button).setOnClickListener(this)
        mWebView?.setWebViewClient(MyWebViewClient())
    }

    override fun handleClick(id: Int, v: View?) {
        if (id == R.id.btn_launch) {
            mWebView?.loadUrl(mEditText?.text.toString())
        }
    }

    inner class MyWebViewClient : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            mEditText?.setText(url)
        }
    }
}