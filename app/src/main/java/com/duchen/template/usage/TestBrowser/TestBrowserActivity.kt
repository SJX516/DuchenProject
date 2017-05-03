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
        mEditText = find(R.id.input_text)
        mLaunchBtn = find(R.id.launch_btn)
        mWebView = find(R.id.webview)
    }

    override fun initViews() {
        (mLaunchBtn as Button).setOnClickListener(this)
        mWebView?.setWebViewClient(MyWebViewClient())
    }

    override fun handleClick(id: Int, v: View?) {
        if (id == R.id.launch_btn) {
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