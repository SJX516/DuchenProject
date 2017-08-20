package com.duchen.template.usage.MutilProcessConnect.intent;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;


public class MsgDistributeService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MsgDistribute.distributeMessage(this.getApplicationContext(), intent);
        return super.onStartCommand(intent, flags, startId);
    }
}
