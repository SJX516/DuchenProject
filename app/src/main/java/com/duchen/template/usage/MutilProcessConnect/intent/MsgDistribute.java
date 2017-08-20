package com.duchen.template.usage.MutilProcessConnect.intent;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.duchen.template.utils.LogUtil;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class MsgDistribute {

    private static volatile MsgDistribute sInstance = null;
    private static volatile ScheduledThreadPoolExecutor sScheduleThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);

    private MsgDistribute() {
    }

    public static MsgDistribute getInstance() {
        if (sInstance == null) {
            synchronized (MsgDistribute.class) {
                if (sInstance == null) {
                    sInstance = new MsgDistribute();
                }
            }
        }
        return sInstance;
    }

    public static void distributeMessage(final Context context, final Intent intent) {
        sScheduleThreadPoolExecutor.execute(new Runnable() {
            public void run() {
                MsgDistribute.getInstance().distribute(context, intent);
            }
        });
    }

    private void distribute(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            return;
        } else {
            try {
                if (TextUtils.equals(action, "com.wudaokou.hdm.intent.action.RECEIVE")) {
                    String msg = intent.getStringExtra("msg");
                    if (intent.getPackage() == null) {
                        intent.setPackage(context.getPackageName());
                    }

                    LogUtil.i("MsgDistribute", "distribute msg: " + msg);

                    if (TextUtils.isEmpty(msg)) {
                        return;
                    } else {
                        if (ConnectClient.getInstance().getReceiver() == null) {
                            LogUtil.e("MsgDistribute", "receiver is null");
                        } else {
                            LogUtil.i("MsgDistribute", "distribute msg to " + ConnectClient.getInstance().getReceiver().getClass().getName());
                            intent.setClassName(context, ConnectClient.getInstance().getReceiver().getClass().getName());
                            context.startService(intent);
                        }
                    }
                } else {
                    LogUtil.e("MsgDistribute", "action error " + action);
                }
            } catch (Throwable e) {
                LogUtil.e("MsgDistribute", "distributeMessage error : " + e);
            }

        }
    }
}
