package com.duchen.template.usage.ScreenShotsAndInstallAPK.without_root;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.duchen.template.usage.ScreenShotsAndInstallAPK.PackageUtil;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;


public class InstallUtils {

    private static final String TAG = "InstallUtils";


    public static void test(Context context) {
//        install(context, Env.getAppStoragePath() + File.separator + "apk" +
//                File.separator + "600000taobao_android_5.2.7.4.apk");
    }

    ///////////////////////////////////////////////////////////////////////////
    // 非Root
    ///////////////////////////////////////////////////////////////////////////

    public static boolean install(Context context, String filePath) {
        PackageInfo waitToInstallApkInfo = PackageUtil.getPackageInfoFromFile(context, filePath);
        if (waitToInstallApkInfo != null && !TextUtils.isEmpty(waitToInstallApkInfo.packageName)) {
            PackageInfo alreadyInstallApkInfo = PackageUtil.getPackageInfo(context, waitToInstallApkInfo.packageName);
            if (alreadyInstallApkInfo != null) {
                //说明已经安装了该包名的app
                if (waitToInstallApkInfo.versionCode >= alreadyInstallApkInfo.versionCode) {
                    return installNormal(context, filePath);
                } else {
                    return installDegrade(context, filePath, waitToInstallApkInfo.packageName);
                }
            }
        }
        return installNormal(context, filePath);
    }


    public static boolean installNormal(Context context, String filePath) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        File file = new File(filePath);
        if (file == null || !file.exists() || !file.isFile() || file.length() <= 0) {
            return false;
        }
        AutoInstallService.mIsAbleToAutoInstall = true;
        AutoInstallService.mInstallFilePath = filePath;

        i.setDataAndType(Uri.fromFile(new File(filePath)),
                "application/vnd.android.package-archive");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        return true;
    }

    public static boolean installDegrade(Context context, String filePath, String packageName) {
        AutoInstallService.mInstallFilePath = filePath;
        AutoInstallService.mShouldInstallAfterUnInstall = true;
        return uninstallNormal(context, packageName);
    }

    public static boolean uninstallNormal(Context context, String packageName) {
        if (packageName == null || packageName.length() == 0) {
            return false;
        }
        AutoInstallService.mIsAbleToAutoUnInstall = true;
        Intent i = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + packageName));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        return true;
    }

    private static String getPackageName(Context context, String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile() || file.length() <= 0) {
            return "";
        }
        try {
            Object pkgParserPkg = getPackage(filePath);
            // 从返回的对象得到名为"applicationInfo"的字段对象
            if (pkgParserPkg == null) {
                return "";
            }
            Field appInfoFld = pkgParserPkg.getClass().getDeclaredField("applicationInfo");
            // 从对象"pkgParserPkg"得到字段"appInfoFld"的值
            if (appInfoFld.get(pkgParserPkg) == null) {
                return "";
            }
            ApplicationInfo info = (ApplicationInfo) appInfoFld.get(pkgParserPkg);
            return info.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 通过反射机制获取应用名称，通过其他方式有可能只得到包名
     * 只适应5.0以下版本
     *
     * @param ctx
     * @param apkPath
     * @return
     */
    public static String getAppNameByReflection(Context ctx, String apkPath) {
        File apkFile = new File(apkPath);
        if (!apkFile.exists()) {//|| !apkPath.toLowerCase().endsWith(".apk")
            return null;
        }
        String PATH_AssetManager = "android.content.res.AssetManager";
        try {
            Object pkgParserPkg = getPackage(apkPath);
            // 从返回的对象得到名为"applicationInfo"的字段对象
            if (pkgParserPkg == null) {
                return null;
            }
            Field appInfoFld = pkgParserPkg.getClass().getDeclaredField(
                    "applicationInfo");
            // 从对象"pkgParserPkg"得到字段"appInfoFld"的值
            if (appInfoFld.get(pkgParserPkg) == null) {
                return null;
            }
            ApplicationInfo info = (ApplicationInfo) appInfoFld.get(pkgParserPkg);

            // 反射得到assetMagCls对象并实例化,无参
            Class<?> assetMagCls = Class.forName(PATH_AssetManager);
            Object assetMag = assetMagCls.newInstance();
            // 从assetMagCls类得到addAssetPath方法
            Class[] typeArgs = new Class[1];
            typeArgs[0] = String.class;
            Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod(
                    "addAssetPath", typeArgs);
            Object[] valueArgs = new Object[1];
            valueArgs[0] = apkPath;
            // 执行assetMag_addAssetPathMtd方法
            assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);

            // 得到Resources对象并实例化,有参数
            Resources res = ctx.getResources();
            typeArgs = new Class[3];
            typeArgs[0] = assetMag.getClass();
            typeArgs[1] = res.getDisplayMetrics().getClass();
            typeArgs[2] = res.getConfiguration().getClass();
            Constructor resCt = Resources.class
                    .getConstructor(typeArgs);
            valueArgs = new Object[3];
            valueArgs[0] = assetMag;
            valueArgs[1] = res.getDisplayMetrics();
            valueArgs[2] = res.getConfiguration();
            res = (Resources) resCt.newInstance(valueArgs);

            PackageManager pm = ctx.getPackageManager();
            // 读取apk文件的信息
            if (info == null) {
                return null;
            }
            String appName;
            if (info.labelRes != 0) {
                appName = (String) res.getText(info.labelRes);
            } else {
                appName = info.loadLabel(pm).toString();
                if (TextUtils.isEmpty(appName)) {
                    appName = apkFile.getName();
                }
            }

            return appName;
        } catch (Exception e) {
            Log.e(TAG, "Exception", e);
        }
        return null;
    }

    /**
     * 通过反射获取Package对象，用来获取ApplicationInfo对象
     * @param apkPath
     * @return
     * @throws Exception
     */
    private static Object getPackage(String apkPath) throws Exception {

        String PATH_PackageParser = "android.content.pm.PackageParser";

        Constructor<?> packageParserConstructor = null;
        Method parsePackageMethod = null;
        Object packageParser = null;
        Class<?>[] parsePackageTypeArgs = null;
        Object[] parsePackageValueArgs = null;

        Class<?> pkgParserCls = Class.forName(PATH_PackageParser);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            packageParserConstructor = pkgParserCls.getConstructor();//PackageParser构造器
            packageParser = packageParserConstructor.newInstance();//PackageParser对象实例
            parsePackageTypeArgs = new Class<?>[]{File.class, int.class};
            parsePackageValueArgs = new Object[]{new File(apkPath), 0};//parsePackage方法参数
        } else {
            Class<?>[] paserTypeArgs = {String.class};
            packageParserConstructor = pkgParserCls.getConstructor(paserTypeArgs);//PackageParser构造器
            Object[] paserValueArgs = {apkPath};
            packageParser = packageParserConstructor.newInstance(paserValueArgs);//PackageParser对象实例

            parsePackageTypeArgs = new Class<?>[]{File.class, String.class,
                    DisplayMetrics.class, int.class};
            DisplayMetrics metrics = new DisplayMetrics();
            metrics.setToDefaults();
            parsePackageValueArgs = new Object[]{new File(apkPath), apkPath, metrics, 0};//parsePackage方法参数

        }
        parsePackageMethod = pkgParserCls.getDeclaredMethod("parsePackage", parsePackageTypeArgs);
        // 执行pkgParser_parsePackageMtd方法并返回
        return parsePackageMethod.invoke(packageParser, parsePackageValueArgs);
    }

    public static String getAppNameByPackage(Context context, String packageName) {
        String appName = null;
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packages.size();i++) {
            PackageInfo packageInfo = packages.get(i);
            if (packageInfo.packageName.equals(packageName)) {
                ApplicationInfo info = packageInfo.applicationInfo;
                appName = info.loadLabel(context.getPackageManager()).toString();
            }
        }
        Log.i(TAG, "getAppNameByPackage: " + appName);
        return appName;
    }
}
