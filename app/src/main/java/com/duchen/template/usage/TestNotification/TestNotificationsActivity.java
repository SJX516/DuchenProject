package com.duchen.template.usage.TestNotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.os.Bundle;
import android.view.View;

import com.duchen.template.usage.ActivityBase;
import com.duchen.template.usage.R;

public class TestNotificationsActivity extends ActivityBase {

    public static final String ACTION = "com.duchen.action.test";
    private BroadcastReceiver mBr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_notifations);
        findViewById(R.id.send_broadcast_special).setOnClickListener(this);
        findViewById(R.id.send_broadcast_normal).setOnClickListener(this);
        registerReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBr);
        unregisterReceiver(mBr);
    }

    private void registerReceiver() {
        mBr =  new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter(ACTION);
//        LocalBroadcastManager.getInstance(this).registerReceiver(mBr, filter);
        registerReceiver(mBr, filter);
    }

    @Override
    public void handleClick(int id, View v) {
        Intent intent = new Intent();
        if (id == R.id.send_broadcast_normal) {
            intent.setAction(ACTION);
            intent.putExtra("type", 0);
            intent.putExtra("title", "normal");
            intent.putExtra("content", "content!!!");
//            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            sendBroadcast(intent);
        } else if (id == R.id.send_broadcast_special) {
            intent.setAction(ACTION);
            intent.putExtra("type", 1);
            intent.putExtra("title", "special");
            intent.putExtra("content", "content!!!");
//            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            sendBroadcast(intent);
        }
    }

    public static class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Intent resultIntent = new Intent(context, TestInvokeActivity.class);
            PendingIntent resultPendingIntent = null;

            if (intent.getIntExtra("type", 0) == 1) {
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                // Adds the back stack
                stackBuilder.addParentStack(TestInvokeActivity.class);
                // Adds the Intent to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                // Gets a PendingIntent containing the entire back stack
                resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            } else {
                resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(R.mipmap.photo);
            builder.setContentIntent(resultPendingIntent);
            builder.setContentTitle(intent.getStringExtra("title"));
            builder.setSubText(intent.getStringExtra("content"));
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, builder.build());
        }
    }

}
