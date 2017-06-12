package com.duchen.template.usage.ScreenShotsAndInstallAPK;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.duchen.template.usage.MainActivity;
import com.duchen.template.usage.ScreenShotsAndInstallAPK.shell.CommandManager;

public class AutoRunReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.MY_PACKAGE_REPLACED")) {
            Intent intent1 = new Intent(context, MainActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        } else if (intent.getAction().equals(CommandManager.UNINSTALL_ACTION)) {
            String name = intent.getStringExtra("packageName");
            if (CommandManager.getInstance().getResultListener() != null && !TextUtils.isEmpty(name)) {
                CommandManager.getInstance().getResultListener().onUnInstallResult(true, name);
            }
        }
    }
}
