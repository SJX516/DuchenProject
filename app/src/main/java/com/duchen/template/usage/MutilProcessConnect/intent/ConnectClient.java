package com.duchen.template.usage.MutilProcessConnect.intent;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * 基于Intent跨应用单向发送消息的demo，需要两个应用都接入这部分代码，然后就可以向对方发送消息了
 */
public class ConnectClient {

    private static volatile ConnectClient sInstance = null;

    private BaseReceiver mBaseService;

    private ConnectClient() {
    }

    public static ConnectClient getInstance() {
        if (sInstance == null) {
            synchronized (ConnectClient.class) {
                if (sInstance == null) {
                    sInstance = new ConnectClient();
                }
            }
        }
        return sInstance;
    }

    public void bindReceiver(BaseReceiver baseService) {
        mBaseService = baseService;
    }

    public BaseReceiver getReceiver() {
        return mBaseService;
    }

    public void sendMsgToHdm(Context context, String msg) {
        sendMsg(context, null, msg);
    }

    public void sendMsg(Context context, String toPackageName, String msg) {
        Intent intent = new Intent();
        intent.setPackage(TextUtils.isEmpty(toPackageName) ? "com.wudaokou.hdm" : toPackageName);
        intent.setAction("com.wudaokou.hdm.intent.action.RECEIVE");
        intent.putExtra("msg", msg);
        intent.putExtra("from", context.getPackageName());
        context.startService(intent);
    }

}
