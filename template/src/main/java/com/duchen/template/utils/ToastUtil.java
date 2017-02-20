package com.duchen.template.utils;

import android.content.Context;
import android.widget.Toast;

import com.duchen.template.component.BaseApplication;


/**
 * 系统Toast封装
 */
public class ToastUtil {

    private static final int TOAST_DURATION = Toast.LENGTH_SHORT;

    public static Toast showToast(Context context, String message, boolean force) {
        if (BaseApplication.getInstance() != null && (BaseApplication.getInstance().hasVisibleActivity() || force)) {
            Toast t = Toast.makeText(context, message, TOAST_DURATION);
            t.show();

            return t;
        }
        return null;
    }

    public static Toast showToast(Context context, int resId, boolean force) {
        if (BaseApplication.getInstance() != null && (BaseApplication.getInstance().hasVisibleActivity() || force)) {
            Toast t = Toast.makeText(context, resId, TOAST_DURATION);
            t.show();

            return t;
        }
        return null;
    }

    public static Toast showToastLong(String message) {
        if (BaseApplication.getInstance() != null && BaseApplication.getInstance().hasVisibleActivity()) {
            Toast t = Toast.makeText(BaseApplication.getInstance(), message, Toast.LENGTH_LONG);
            t.show();

            return t;
        }
        return null;
    }

    public static Toast showToastLong(int resId) {
        if (BaseApplication.getInstance() != null && BaseApplication.getInstance().hasVisibleActivity()) {
            Toast t = Toast.makeText(BaseApplication.getInstance(), resId, Toast.LENGTH_LONG);
            t.show();

            return t;
        }
        return null;
    }

    public static Toast showToast(Context context, String message) {
        return showToast(context, message, false);
    }

    public static Toast showToast(String message) {
        return showToast(BaseApplication.getInstance(), message, false);
    }

    public static Toast showToast(int resId) {
        return showToast(BaseApplication.getInstance(), resId, false);
    }
}
