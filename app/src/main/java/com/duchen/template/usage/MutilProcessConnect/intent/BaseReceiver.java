package com.duchen.template.usage.MutilProcessConnect.intent;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public abstract class BaseReceiver extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String msg = intent.getStringExtra("msg");
        if (!TextUtils.isEmpty(msg)) {
            onData(msg);
        }
        return Service.START_NOT_STICKY;
    }

    public abstract void onData(String data);
}
