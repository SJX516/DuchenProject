package com.duchen.template.usage.TouchMonitor;

import android.content.Context;

public class TouchManager {

    private static final String TAG = "TouchManager";

    private static TouchManager sInstance;

    public static TouchManager getInstance() {
        if (sInstance == null) {
            sInstance = new TouchManager();
        }
        return sInstance;
    }

    public interface OnEventListener {
        void onEvent();
    }

    OnEventListener mListener;

    private TouchManager() {
    }

    public void setListener(OnEventListener listener) {
        mListener = listener;
    }

    //多久没触摸调用一次 onUnTouch，ms单位
    public long getUnTouchTickTime() {
        return 60 * 1000;
    }

    public void onUnTouchTick(int count) {
        if (mListener != null) {
            if (count == 2) {
                mListener.onEvent();
            }
        }
    }

    public void init(Context context) {
        TouchMonitorService.startService(context);
    }

    public void reset(Context context) {
        TouchMonitorService.refreshService(context);
    }

    public void stop(Context context) {
        TouchMonitorService.stopService(context);
    }
}
