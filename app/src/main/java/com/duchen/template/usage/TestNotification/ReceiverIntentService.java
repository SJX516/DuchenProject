package com.duchen.template.usage.TestNotification;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.duchen.template.utils.ToastUtil;

public class ReceiverIntentService extends IntentService{

    public ReceiverIntentService() {
        super("ReceiverIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //StudyPoint 由于 toast 会使用当前 thread 来执行 show 的操作,所以需要切换到主线程执行
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showToast("application ReceiverIntentService");
            }
        });
    }
}
