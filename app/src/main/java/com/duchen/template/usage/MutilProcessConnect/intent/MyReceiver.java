package com.duchen.template.usage.MutilProcessConnect.intent;

import com.duchen.template.utils.DLog;

public class MyReceiver extends BaseReceiver {

    @Override
    public void onData(String data) {
        DLog.i("HdmReceiver", "received: " + data);
    }
}