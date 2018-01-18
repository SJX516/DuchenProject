package com.duchen.template.utils;

import android.util.Log;
import android.view.MotionEvent;

public class DLog {

    public final static String TAG_MODEL_CHECK = "LegalModelCheck";

    public final static String TAG_QUICK = "Duchen";

    private static final int VERBOSE = 2; // Log.VERBOSE
    private static final int DEBUG = 3; // Log.DEBUG
    private static final int INFO = 4; // Log.INFO
    private static final int WARN = 5; // Log.WARN
    private static final int ERROR = 6; // Log.ERROR

    public static void d(String tag, String msg) {
        log(tag, msg, DEBUG);
    }

    public static void d(String msg) {
        log(TAG_QUICK, msg, DEBUG);
    }

    public static void v(String tag, String msg) {
        log(tag, msg, VERBOSE);
    }

    public static void e(String tag, String msg) {
        log(tag, msg, ERROR);
    }

    public static void i(String tag, String msg) {
        log(tag, msg, INFO);
    }

    public static void w(String tag, String msg) {
        log(tag, msg, WARN);
    }

    private static void log(String tag, String msg, int level) {
        if (tag == null) tag = "TAG_NULL";
        if (msg == null) msg = "MSG_NULL";

        logToConsole(tag, msg, level);
    }

    private static void logToConsole(String tag, String msg, int level) {
        switch (level) {
            case Log.DEBUG:
                Log.d(tag, msg);
                break;
            case Log.INFO:
                Log.i(tag, msg);
                break;
            case Log.VERBOSE:
                Log.v(tag, msg);
                break;
            case Log.WARN:
                Log.w(tag, msg);
                break;
            default:
            case Log.ERROR:
                Log.e(tag, msg);
                break;
        }
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
