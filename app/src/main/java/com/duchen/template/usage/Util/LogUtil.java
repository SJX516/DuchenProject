package com.duchen.template.usage.Util;

import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by netease on 17/1/17.
 */
public class LogUtil {

    public static final String TAG = "Duchen";

    public static void LogD(String message) {
        Log.d(TAG, message);
    }

    public static void LogActivityD(String message) {
        Log.d(TAG, message);
    }

    public static String eventToString(int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            case MotionEvent.ACTION_CANCEL:
                return "ACTION_CANCEL";
            default:
                return "";
        }
    }
}
