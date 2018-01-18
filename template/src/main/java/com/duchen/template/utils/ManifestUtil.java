package com.duchen.template.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class ManifestUtil {

    private static final String TAG = "ManifestUtils";

    /**
     * Get current version name.
     */
    public static String getApplicationVersionName(Context context) {
        String version = "?";
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            DLog.e(TAG, e.getMessage());
        }
        return version;
    }

    /**
     * Get current version code.
     */
    public static int getApplicationVersionCode(Context context) {
        int code = 0;
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            code = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            DLog.e(TAG, e.getMessage());
        }
        return code;
    }

    /**
     * Get application name.
     */
    public static String getApplicationName(Context context) {
        String name = "?";
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            name = context.getString(pi.applicationInfo.labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            DLog.e(TAG, e.getMessage());
        }
        return name;
    }

    /**
     * 获取AndroidManifest中<metadata>标签定义的值
     */
    public static String getApplicationMetaData(Context context, String metaName) {
        PackageManager pm = context.getPackageManager();
        ApplicationInfo info;
        try {
            info = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (info != null && info.metaData != null) {
                return info.metaData.getString(metaName);
            } else {
                return null;
            }
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    /**
     * 获取AndroidManifest中<metadata>标签定义的值
     */
    public static int getApplicationMetaDataInt(Context context, String metaName) {
        PackageManager pm = context.getPackageManager();
        ApplicationInfo info;
        try {
            info = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (info != null && info.metaData != null) {
                return info.metaData.getInt(metaName);
            } else {
                return 0;
            }
        } catch (NameNotFoundException e) {
            return 0;
        }

    }

}
