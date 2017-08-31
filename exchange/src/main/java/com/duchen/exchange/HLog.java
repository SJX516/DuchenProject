package com.duchen.exchange;

import android.util.Log;

public class HLog {

    private static String preTag = "dc_exchange.";
    private static Object LOG_BREAK = "|";
    private static boolean isPrintLog = true;

    public static void setPrintLog(boolean printLog) {
        isPrintLog = printLog;
    }

    private static boolean isPrintLog() {
        return isPrintLog;
    }

    public static void d(String tag, String msg) {
        if(isPrintLog()) {
            Log.d(buildLogTag(tag), msg);
        }
    }

    public static void i(String tag, String msg) {
        if(isPrintLog()) {
            Log.i(buildLogTag(tag), msg);
        }
    }

    public static void e(String tag, String msg) {
        if(isPrintLog()) {
            Log.e(buildLogTag(tag), msg);
        }

    }

    private static String buildLogTag(String tag) {
        return preTag + tag;
    }
}
