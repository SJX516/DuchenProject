package com.duchen.template.usage.ScreenShotsAndInstallAPK;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.Toast;

public class PackageUtil {

    public static PackageInfo getPackageInfoFromFile(Context context, String filePath) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.getPackageArchiveInfo(filePath, 0);
    }

    public static PackageInfo getPackageInfo(Context context, String packName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    public static boolean launchAppFromPath(Context context, String filePath) {
        PackageInfo packageInfo = getPackageInfoFromFile(context, filePath);
        if (packageInfo != null && !TextUtils.isEmpty(packageInfo.packageName)) {
            return launchApp(context, packageInfo.packageName);
        } else {
            return false;
        }
    }

    public static boolean launchApp(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        if (getPackageInfo(context, packageName) != null) {
            Intent intent = packageManager.getLaunchIntentForPackage(packageName);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } else {
            Toast.makeText(context, "没有安装" + packageName, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
