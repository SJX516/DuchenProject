package com.duchen.template.usage.ScreenShotsAndInstallAPK.systemapp;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInstaller;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;

import com.duchen.template.component.ApplicationBase;

import java.io.File;
import java.lang.reflect.Method;

public class AIDLCommand {

    public static void onInstall(String filePath)
    {
        File apkFile = new File(filePath);
        try {
            Class<?> clazz = Class.forName("android.os.ServiceManager");
            Method method_getService = clazz.getMethod("getService", String.class);
            IBinder bind = (IBinder) method_getService.invoke(null, "package");

            IPackageManager iPm = IPackageManager.Stub.asInterface(bind);
            iPm.installPackage(Uri.fromFile(apkFile), null, 2, apkFile.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void onUnInstall(String packageName) {
//        //该应用并没有被安装
//        if (TextUtils.isEmpty(packageName) || PackageUtil.getPackageInfo(ApplicationBase.getInstance(), packageName) == null) {
//            return;
//        }
//        try {
//            Class<?> clazz = Class.forName("android.os.ServiceManager");
//            Method method_getService = clazz.getMethod("getService", String.class);
//            IBinder bind = (IBinder) method_getService.invoke(null, "package");
//
//            IPackageManager iPm = IPackageManager.Stub.asInterface(bind);
//            iPm.deletePackage(packageName, null, 1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Intent intent = new Intent(ApplicationBase.getInstance(), ApplicationBase.getInstance().getClass());
        PendingIntent sender = PendingIntent.getActivity(ApplicationBase.getInstance(), 0, intent, 0);
        PackageInstaller packageInstaller = ApplicationBase.getInstance().getPackageManager().getPackageInstaller();
        packageInstaller.uninstall(packageName, sender.getIntentSender());
    }
}
