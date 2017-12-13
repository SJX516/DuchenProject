package com.duchen.template.usage.TouchMonitor;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.duchen.template.usage.R;

public class TouchMonitorService extends Service {

    private static String TAG = "TouchMonitorService";

    public static long sUnTouchDefaultTickTime = 60 * 1000;

    private View mFloatView;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWmParams;
    protected Handler mHandler;
    private long mUnTouchTickTime = 0;
    private boolean mIsCountDown = false;
    private int mUnTouchTickCount = 0;

    public static void startService(Context context) {
        Intent intent = new Intent(context, TouchMonitorService.class);
        context.startService(intent);
    }

    public static void refreshService(Context context) {
        Intent intent = new Intent(context, TouchMonitorService.class);
        intent.putExtra("refresh", true);
        context.startService(intent);
    }

    public static void stopService(Context context) {
        Intent intent = new Intent(context, TouchMonitorService.class);
        context.stopService(intent);
    }

    public TouchMonitorService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createFloatView();
        long tickTime = TouchManager.getInstance().getUnTouchTickTime();
        if (tickTime <= 1000) {
            mUnTouchTickTime = sUnTouchDefaultTickTime;
        }
        mUnTouchTickTime = tickTime;
        mHandler = new Handler();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
        removeFloatView();
    }

    private void removeFloatView() {
        if (mFloatView != null && mWindowManager != null) {
            mWindowManager.removeView(mFloatView);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            startCountDown(intent.getBooleanExtra("refresh", false));
        } else {
            startCountDown(false);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void createFloatView() {
        mWindowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        initWindowParams();
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        mFloatView = inflater.inflate(R.layout.view_float_touch_monitor, null);
        mFloatView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startCountDown(true);
                return false;
            }
        });
        mWindowManager.addView(mFloatView, mWmParams);
    }

    private void startCountDown(boolean needRefresh) {
        if (needRefresh || !mIsCountDown) {
            mUnTouchTickCount = 0;
            mIsCountDown = true;
            delayTask();
        }
    }

    void delayTask() {
        mHandler.removeCallbacks(mRunnable);
        mHandler.postDelayed(mRunnable, mUnTouchTickTime);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mUnTouchTickCount++;
            TouchManager.getInstance().onUnTouchTick(mUnTouchTickCount);
            delayTask();
        }
    };

    private void initWindowParams() {
        mWmParams = new WindowManager.LayoutParams();
        mWmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mWmParams.format = PixelFormat.RGBA_8888;
        mWmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        mWmParams.gravity = Gravity.LEFT | Gravity.TOP;
        mWmParams.x = 0;
        mWmParams.y = 0;
        mWmParams.width = 1;
        mWmParams.height =  1;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
