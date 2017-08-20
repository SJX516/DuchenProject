package com.duchen.template.usage.MutilProcessConnect.intent;

import com.duchen.template.utils.LogUtil;

public class MyReceiver extends BaseReceiver {

    @Override
    public void onData(String data) {
        LogUtil.i("HdmReceiver", "received: " + data);
    }
}