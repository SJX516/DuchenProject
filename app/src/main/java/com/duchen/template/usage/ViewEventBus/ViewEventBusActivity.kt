package com.duchen.template.usage.ViewEventBus

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.duchen.template.usage.AppActivityBase
import com.duchen.template.usage.R
import de.greenrobot.event.EventBus
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

class ViewEventBusActivity : AppActivityBase() {

    var mSendBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mEventBus.register(this)
    }

    override fun setContentView() {
        setContentView(R.layout.activity_view_event_bus)
    }

    override fun findViews() {
        mSendBtn = find(R.id.btn_send)
    }

    override fun initViews() {
        mSendBtn?.setOnClickListener(this)
    }

    override fun handleClick(id: Int, v: View?) {
        if (id == R.id.btn_send) {
            val data : String = "post text"
            EventBus.getDefault().post(data)
        }
    }

    fun onEventMainThread(text : String) {
        toast("onEventMainThread : " + text)
    }

}
