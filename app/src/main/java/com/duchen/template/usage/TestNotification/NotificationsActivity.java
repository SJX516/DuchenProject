package com.duchen.template.usage.TestNotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;

import com.duchen.template.usage.AppActivityBase;
import com.duchen.template.usage.R;

/**
 * 测试notification唤起app的情况 (可以唤起子Activity并关联父Activity)
 */
public class NotificationsActivity extends AppActivityBase {

    public static final String ACTION = "com.duchen.action.test";
    private BroadcastReceiver mBr;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBr);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_test_notifations);
    }

    @Override
    public void findViews() {
        findViewById(R.id.send_broadcast_special).setOnClickListener(this);
        findViewById(R.id.send_broadcast_normal).setOnClickListener(this);
    }

    @Override
    public void initViews() {
        registerReceiver();
    }

    private void registerReceiver() {
        mBr =  new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter(ACTION);
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
            sendBroadcast(intent);
        } else if (id == R.id.send_broadcast_special) {
            intent.setAction(ACTION);
            intent.putExtra("type", 1);
            intent.putExtra("title", "special");
            intent.putExtra("content", "content!!!");
            sendBroadcast(intent);
        }
    }

    public static class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Intent resultIntent = new Intent(context, InvokeActivity.class);
            PendingIntent resultPendingIntent;

            //StudyPoint 通过构建一个 TaskStack ,当不存在主页面时,在子页面点击返回也可以返回到主页面,可以得到更良好的导航体验。
            // 需要在 manifest 中 添加 activity 相应的 parentActivityName 属性配置
            if (intent.getIntExtra("type", 0) == 1) {
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                // Adds the back stack
                stackBuilder.addParentStack(InvokeActivity.class);
                // Adds the Intent to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                // Gets a PendingIntent containing the entire back stack
                resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
            } else {
                resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(R.mipmap.photo);
            builder.setContentIntent(resultPendingIntent);
            builder.setContentTitle(intent.getStringExtra("title"));
            builder.setContentText(intent.getStringExtra("content"));
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, builder.build());

            Intent startServiceIntent = new Intent(context, ReceiverIntentService.class);
            context.startService(startServiceIntent);
        }
    }

}
