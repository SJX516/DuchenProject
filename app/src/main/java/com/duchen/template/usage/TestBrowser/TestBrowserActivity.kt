package com.duchen.template.usage.TestBrowser

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.duchen.template.usage.AppActivityBase
import com.duchen.template.usage.R
import org.jetbrains.anko.find

class TestBrowserActivity : AppActivityBase() {

    var mEditText : EditText? = null
    var mLaunchBtn : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_browser)
        mEditText = find(R.id.input_text)
        mLaunchBtn = find(R.id.launch_btn)
        (mLaunchBtn as Button).setOnClickListener(this)
    }

    override fun handleClick(id: Int, v: View?) {
        if (id == R.id.launch_btn) {
            BrowserActivity.launch(this, mEditText?.text.toString())
        }
    }
}