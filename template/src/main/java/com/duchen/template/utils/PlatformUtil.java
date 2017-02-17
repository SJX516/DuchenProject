package com.duchen.template.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.duchen.template.component.BaseApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * **************************framework PlatformUtil工具类**************************<br>
 * android版本号信息<br>
 * 获取设备ID<br>
 * 获取OS版本<br>
 * 获取设备名字<br>
 * 获取屏幕分辨率 <br>
 * 获取应用名称<br>
 * 网络是否已连接,是否是wifi还是3G<br>
 * 获取app versionname versioncode<br>
 * 获取当前进程名<br>
 * 检查apk 是否安装<br>
 * 获取wifi的mac和ip地址<br>
 * 获取手机IMEI值<br>
 */
public class PlatformUtil {

    private final static String TAG = "PlatformUtil";

    public static final int Android_SDK_1_0 = 1;
    public static final int Android_SDK_1_1 = 2;
    public static final int Android_SDK_1_5 = 3;
    public static final int Android_SDK_1_6 = 4;
    public static final int Android_SDK_2_0 = 5;
    public static final int Android_SDK_2_0_1 = 6;
    public static final int Android_SDK_2_1 = 7;
    public static final int Android_SDK_2_2 = 8;
    public static final int Android_SDK_2_3_1 = 9;
    public static final int Android_SDK_2_3_3 = 10;
    public static final int Android_SDK_3_0 = 11;
    public static final int Android_SDK_3_1 = 12;
    public static final int Android_SDK_3_2 = 13;
    public static final int Android_SDK_4_0 = 14;
    public static final int Android_SDK_4_0_3 = 15;
    public static final int Android_SDK_4_1 = 16;
    public static final int Android_SDK_4_2 = 17;
    public static final int Android_SDK_6_0 = 23;

    /**
     * 获取设备ID
     *
     * @param context
     * @return
     */
    public static String getDeviceID(Context context) {
        String imei = null;
        String androidId = null;
        try {
            imei = getPhoneIMEI(context);
            if (TextUtils.isEmpty(imei)) {
                imei = getWifiMacAddress(context);
            }

            androidId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider
                    .Settings.Secure.ANDROID_ID);
            if (!TextUtils.isEmpty(androidId)) {
                imei += "_" + androidId;
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }

        if (imei == null) {
            imei = "";
        }
        return imei;
    }

    /**
     * 获取设备ID，若获取不到imei，则返回一个UUID
     *
     * @param context
     * @return
     */
    public static String getDeviceIDWhenNullReturnUUID(Context context) {
        String imei = getDeviceID(context);
        if (TextUtils.isEmpty(imei)) {
            UUID uuid = UUID.randomUUID();
            imei = uuid.toString();
        }
        return imei;
    }

    /**
     * 获取OS版本
     *
     * @return
     */
    public static String getOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取设备名字
     *
     * @return
     */
    public static String getMobileName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    /**
     * 获取屏幕分辨率
     *
     * @return
     */
    public static String getResolution(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager WM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WM.getDefaultDisplay().getMetrics(dm);
        StringBuffer buffer = new StringBuffer();
        buffer.append(dm.widthPixels).append('x').append(dm.heightPixels);
        return buffer.toString();
    }

    public static int getDisplayWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        return wm.getDefaultDisplay().getWidth();
    }

    public static int getDisplayHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * 获取应用名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager
                    .GET_CONFIGURATIONS).versionName;
        } catch (NameNotFoundException e) {
            LogUtil.e(TAG, e.getMessage());
            return "";
        }
    }

    /**
     * 网络是否已连接
     *
     * @param context
     * @return
     */
    public static boolean hasConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {

        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (null != info[i] && info[i].isAvailable() && info[i].isConnectedOrConnecting()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 是否是2G3G4G网络
     *
     * @param context
     * @return
     */
    public static boolean isMobileNetWork(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.getType() ==
                ConnectivityManager.TYPE_MOBILE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否是wifi网络
     *
     * @param context
     * @return
     */
    public static boolean isWifiNetWork(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.getType() ==
                ConnectivityManager.TYPE_WIFI) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取app versioncode
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            LogUtil.e(TAG, e.getMessage());
            return 0;
        }
    }

    /**
     * 获取app versionname
     */
    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            LogUtil.e(TAG, e.getMessage());
            return "";
        }
    }

    /**
     * 获取应用版本号
     */
    public static String swSimpleVersionStr(Context context) {
        String str = "";
        try {
            str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            str = str.trim();
        } catch (NameNotFoundException e) {
            LogUtil.e(TAG, e.getMessage());
        }

        if (str != null && str.length() > 0) {
            int index = str.indexOf(' ');
            if (index > 0) {
                return str.substring(0, index);
            }
        }
        return str;
    }

    public static long getAvailableExternalMemorySize() {
        if (hasStorage()) {
            File path = Environment.getExternalStorageDirectory();

            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks*blockSize;
        }
        return -1;
    }

    public static boolean hasStorage() {
        return hasStorage(null);
    }

    public static boolean hasStorage(String testWritePath) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if (testWritePath != null) {
                return checkFsWritable(testWritePath);
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查文件系统是否可写
     */
    private static boolean checkFsWritable(String testWritePath) {
        File directory = new File(testWritePath);
        if (!directory.isDirectory()) {
            if (!directory.mkdirs()) {
                return false;
            }
        }

        return directory.canWrite();
    }

    /**
     * 会过滤不能写的分区
     */
    public static HashSet<String> getExternalMounts() {
        final HashSet<String> out = new HashSet<String>();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            out.add(Environment.getExternalStorageDirectory().getPath());
        }
        String reg = ".* (vfat|ntfs|exfat|fat32|fuse) .*rw.*";
        try {
            final Process process = new ProcessBuilder().command("mount").redirectErrorStream(true).start();
            process.waitFor();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.toLowerCase(Locale.US).contains("asec") && !line.toLowerCase(Locale.US).contains("obb") &&
                        !line.toLowerCase(Locale.US).contains("secure")) {
                    if (line.matches(reg)) {
                        String[] parts = line.split(" ");
                        for (int i = 1; i < parts.length; i++) {
                            String part = parts[i];
                            if (part.startsWith("/")) {
                                if (new File(part).canWrite()) {
                                    boolean needAdd = true;//nexus 4的两个目录非常奇怪，可能是内部做了替换 [/storage/emulated/legacy,
                                    // /storage/emulated/0]
                                    String tmpFilename = System.currentTimeMillis() + "";
                                    File f = new File(part, tmpFilename);
                                    f.createNewFile();
                                    for (String o : out) {
                                        if (new File(o, tmpFilename).exists()) {
                                            needAdd = false;
                                            break;
                                        }
                                    }
                                    f.delete();
                                    if (needAdd) {
                                        out.add(part);
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return out;
    }

    /**
     * 获取手机IMEI值
     *
     * @param context
     * @return
     */
    public static String getPhoneIMEI(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mTelephonyMgr.getDeviceId();
        return imei;
    }

    /**
     * 获取wifi的mac地址
     *
     * @param context
     * @return
     */
    public static String getWifiMacAddress(Context context) {
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info.getMacAddress();
        } catch (Exception e) {
            return "";
        }
    }

    // just support some devices and emulator, after connected network, maybe
    // get
    // some devices maybe return "" or null
    public static String getPhoneNumber(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        String phonenumber = mTelephonyMgr.getLine1Number();
        return phonenumber;
    }

    /**
     * 检查是否有intent的接收者
     *
     * @param context
     * @param intent
     * @return
     */
    public static boolean hasIntentRecevicer(Context context, Intent intent) {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(intent, 0);
        return list != null && list.size() > 0;
    }


    /**
     * 获取WIFI IP地址
     *
     * @param context
     * @return
     */
    public static String getWifiIpAddress(Context context) {
        String ipAddress = null;

        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifi != null) {
            DhcpInfo info = wifi.getDhcpInfo();
            if (info != null) {
                ipAddress = toAddress(info.ipAddress);
            }
        }

        return ipAddress;
    }

    private static String toAddress(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                ((i >> 24) & 0xFF);
    }


    /*************** hardware ****************/

    /*************** screen ***************/
    /**
     * return value like width/height
     */
    private static int mDeviceWidth;
    private static int mDeviceHeight;
    private static float mDeviceDensity;

    static public float getDisplayDensity(Context context) {
        if (mDeviceDensity <= 0.1) {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            mDeviceDensity = dm.density;
        }

        return mDeviceDensity;
    }

    public static int[] getDisplayMetrics(Context context) {
        if (mDeviceWidth > 0 && mDeviceHeight > 0) {
        } else {
            int width = 0;
            int height = 0;

            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            display.getMetrics(dm);

            if (getSDKVersionNumber() >= Android_SDK_2_0) {
                width = dm.widthPixels;
                height = dm.heightPixels;
            } else {
                width = (int) (dm.widthPixels/dm.density);
                height = (int) (dm.heightPixels/dm.density);
            }

            int sdkVersion;
            try {
                sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
            } catch (NumberFormatException e) {
                sdkVersion = 5;
            }
            if (sdkVersion >= 13) {
                try {
                    Method mGetRawH = Display.class.getMethod("getRawWidth");
                    Method mGetRawW = Display.class.getMethod("getRawHeight");
                    width = (Integer) mGetRawW.invoke(display);
                    height = (Integer) mGetRawH.invoke(display);
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                }
            }

            if (width < height) {
                mDeviceWidth = width;
                mDeviceHeight = height;
            } else {
                mDeviceWidth = height;
                mDeviceHeight = width;
            }
        }

        return new int[]{mDeviceWidth, mDeviceHeight};
    }

    public static int getSDKVersionNumber() {
        int sdkVersion;
        try {
            sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            sdkVersion = 5;
        }
        return sdkVersion;

    }

    static public String getOSVersionName() {
        return Build.VERSION.RELEASE;

    }

    // just for statistic , they defined 2.0.1 as 2.0Later

    // must to do ??
    public static String getOSVersionNameForStatis() {
        switch (getSDKVersionNumber()) {
            case Android_SDK_2_0_1:
                return "2.0Later";
            default:
                break;
        }

        return getOSVersionName();
    }

    public static boolean allowBindBlogByBrowser() {
        final int number = getSDKVersionNumber();

        return number < Android_SDK_2_2 ? true : false;
    }

    /*************** screen ****************/

    /************* Language *********************/

    /**
     * return en-us
     *
     * @param context
     * @return
     */
    static public String getLocalLanguage(Context context) {
        Locale local = Locale.getDefault();
        return local.getLanguage() + "-" + local.getCountry();

    }

    /************ Language ******************************/

    /*********
     * Space
     **************/

    public static boolean enoughSpaceBySize(String path, long size) {
        if (TextUtils.isEmpty(path)) return true;

        StatFs statFS = new StatFs(path);
        return (long) statFS.getAvailableBlocks()*statFS.getBlockSize() > size;
    }

    public static long availableSize(String path) {
        StatFs statFS = new StatFs(path);
        return (long) statFS.getAvailableBlocks()*statFS.getBlockSize();
    }

    public static boolean isSupportMultiPointer() {
        boolean multiTouchAvailable1 = false;
        boolean multiTouchAvailable2 = false;
        // Not checking for getX(int), getY(int) etc 'cause I'm lazy
        Method methods[] = MotionEvent.class.getDeclaredMethods();
        for (Method m : methods) {
            if (m.getName().equals("getPointerCount")) {
                multiTouchAvailable1 = true;
            }
            if (m.getName().equals("getPointerId")) {
                multiTouchAvailable2 = true;
            }
        }

        if (multiTouchAvailable1 && multiTouchAvailable2) {
            return true; // 支持多点触摸
        } else {
            return false;
        }
    }

    /***************************/
    /***
     * 检查apk 是否安装
     ***/
    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();// 用于存储所有已安装程序的包名
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }

    public static boolean inMainProcess() {
        String packageName = BaseApplication.getInstance().getPackageName();
        String processName = getProcessName(BaseApplication.getInstance());
        return packageName.equals(processName);
    }

    /**
     * 获取当前进程名
     */
    public static final String getProcessName(Context context) {
        String processName = null;

        // ActivityManager
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        if (am == null || am.getRunningAppProcesses() == null) {
            return "";
        }
        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;

                    break;
                }
            }

            // go home
            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }

            // take a rest and again
            try {
                Thread.sleep(100L);
            } catch (InterruptedException ex) {
                LogUtil.e(TAG, ex.getMessage());
            }
        }
    }

    public static Bitmap pickImgFromLocalPictures(Context context, Uri uri) {
        Bitmap bitmap = null;
        if (uri == null) return bitmap;
        // content 4.4以前
        try {
            if (!uri.getScheme().equalsIgnoreCase("file")) {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    bitmap = BitmapFactory.decodeFile(filePath);
                    int orientation = getCameraPhotoOrientation(context, uri, filePath);
                    Matrix mat = new Matrix();
                    mat.postRotate(orientation);
                    if (bitmap != null) {
                        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);
                    }
                }
            }
            // file 4.4以后
            else {
                int orientation = getCameraPhotoOrientation(context, uri, uri.getPath());
                InputStream in = new FileInputStream(uri.getPath());
                bitmap = ImageUtil.getBitmap(in, 0, 0);

                Matrix mat = new Matrix();
                mat.postRotate(orientation);
                if (bitmap != null) {
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);

                }
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return bitmap;
    }

    private static int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath) {
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return rotate;
    }

}
