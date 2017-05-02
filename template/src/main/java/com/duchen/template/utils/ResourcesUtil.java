package com.duchen.template.utils;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.duchen.template.component.ApplicationBase;


public class ResourcesUtil {

    public static Drawable getDrawable(@DrawableRes int id) {
        return ApplicationBase.getInstance().getResources().getDrawable(id);
    }

    public static String getString(@StringRes int id) {
        return ApplicationBase.getInstance().getResources().getString(id);
    }

    public static int getColor(@ColorRes int id) {
        int ret = 0x000000;
        try {
            ret = ApplicationBase.getInstance().getResources().getColor(id);
        } catch (Exception e) {
            // do nothing
        }

        return ret;
    }

}
