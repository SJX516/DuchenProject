package com.duchen.template.utils.storage;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.text.TextUtils;

import com.duchen.template.component.ApplicationBase;
import com.duchen.template.utils.DLog;
import com.duchen.template.utils.PermissionUtil;
import com.duchen.template.utils.PlatformUtil;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * **************************SDCard相关工具类**************************<br>
 * 获取所有SD卡根路径列表<br>
 * 获取默认SD卡根路径<br>
 * 获取磁盘空间信息<br>
 * 获取磁盘总大小<br>
 * 获取剩余空间<br>
 * sdcard是否可用<br>
 * sdcard是否只读<br>
 */
public class StorageUtil {

    private final static String TAG = "StorageUtil";

    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return blockSize * availableBlocks;
    }

    public static long getAvailableExternalMemorySize() {
        if (hasStorage()) {
            File path = Environment.getExternalStorageDirectory();

            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
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
     * 获取所有SD卡根路径列表
     *
     * @return
     */
    public static List<String> getSDCardRootPathList() {
        List<String> pathList = getWritableVolumePaths();
        final String defaultPath = getDefaultSDCardRootPath();

        if (pathList.isEmpty()) {
            if (!TextUtils.isEmpty(defaultPath)) {
                DLog.e(TAG, "getSDCardRootPathList defaultPath = " + defaultPath);
                pathList.add(defaultPath);
            }
        }
        return pathList;
    }

    /**
     * 获取默认SD卡根路径
     *
     * @return
     */
    public static String getDefaultSDCardRootPath() {
        File file = ApplicationBase.getInstance().getExternalCacheDir();
        if (file != null) {
            List<String> pathList = getWritableVolumePaths();
            String path = file.getAbsolutePath();
            for (String aPath : pathList) {
                if (path.startsWith(aPath)) {
                    return aPath;
                }
            }
        } else {
            DLog.e(TAG, "getDefaultSDCardRootPath file is NULL");
            if (Environment.getExternalStorageDirectory() == null) {
                return "";
            } else {
                return Environment.getExternalStorageDirectory().getPath();
            }
        }
        return "";
    }

    /**
     * 反射调用api，获取所有存储器路径列表
     * 获取sdcard的路径：外置和内置
     */
    private static List<String> getWritableVolumePaths() {
        String[] paths = null;
        try {
            StorageManager sm = (StorageManager) ApplicationBase.getInstance().getSystemService(Context
                    .STORAGE_SERVICE);
            paths = (String[]) sm.getClass().getMethod("getVolumePaths", new Class[0]).invoke(sm, new Object[]{});
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException |
                NoSuchMethodException e) {
            DLog.e(TAG, e.getMessage());
        }

        List<String> writablePaths = new ArrayList<String>();
        if (paths != null && paths.length > 0) {
            for (String path : paths) {
                File file = new File(path);
                if (canWrite(file) && file.canRead() && getTotalSize(path) > 0) {
                    writablePaths.add(path);
                }
            }
        }
        return writablePaths;
    }

    public static boolean canWrite(final File file) {
        if (file == null || !file.exists()) {
            return false;
        }

        boolean result;
        if (Build.VERSION.SDK_INT >= PlatformUtil.Android_SDK_6_0) {
            result = PermissionUtil.checkPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            result = file.canWrite();
        }
        return result;
    }


    /**
     * 获取磁盘空间信息
     *
     * @param stringFormat 总共%s，剩余%s
     * @param formatType   类型-1--总共%s,2--剩余%s,3--总共+剩余，4--剩余+总共
     * @return
     */
    public static String getDiskInfo(final String pathRoot, final String stringFormat, final int formatType) {
        if (TextUtils.isEmpty(stringFormat)) {
            return "";
        }

        String info = "";
        try {
            android.os.StatFs statfs = new android.os.StatFs(pathRoot);
            // 获取SDCard上每个block的SIZE
            long nBlocSize = statfs.getBlockSize();
            // 获取可供程序使用的Block的数量
            long nAvailaBlock = statfs.getAvailableBlocks();
            // 计算 SDCard 剩余大小B
            String strSDFreeSize = FileUtil.formatFileSize(nAvailaBlock*nBlocSize);
            // 获取所有Block的数量
            long nTotalBlock = statfs.getBlockCount();
            // 计算 SDCard 总大小B
            String strSDTotalSize = FileUtil.formatFileSize(nTotalBlock*nBlocSize);

            switch (formatType) {
                case 1:
                    info = String.format(stringFormat, strSDTotalSize);
                    break;
                case 2:
                    info = String.format(stringFormat, strSDFreeSize);
                    break;
                case 3:
                    info = String.format(stringFormat, strSDTotalSize, strSDFreeSize);
                    break;
                case 4:
                    info = String.format(stringFormat, strSDFreeSize, strSDTotalSize);
                    break;
                default:
                    return "";
            }
        } catch (Exception e) {
            DLog.e(TAG, e.getMessage());
        }
        return info;
    }

    /**
     * 获取磁盘总大小
     *
     * @param pathRoot
     * @return
     */
    public static long getTotalSize(final String pathRoot) {
        try {
            android.os.StatFs statfs = new android.os.StatFs(pathRoot);
            // 获取SDCard上每个block的SIZE
            long nBlocSize = statfs.getBlockSize();
            // 获取总共的Block的数量
            long nTotalBlock = statfs.getBlockCount();
            return (nTotalBlock*nBlocSize);
        } catch (Exception e) {
            DLog.e(TAG, e.getMessage());
        }
        return 0;
    }

    /**
     * 获取剩余空间
     *
     * @param pathRoot
     * @return
     */
    public static long getAvailableSize(final String pathRoot) {
        try {
            android.os.StatFs statfs = new android.os.StatFs(pathRoot);
            // 获取SDCard上每个block的SIZE
            long nBlocSize = statfs.getBlockSize();
            // 获取可供程序使用的Block的数量
            long nAvailaBlock = statfs.getAvailableBlocks();
            // 计算 SDCard 剩余大小B
            return (nAvailaBlock*nBlocSize);
        } catch (Exception e) {
            DLog.e(TAG, e.getMessage());
        }
        return 0;
    }

    /**
     * sdcard是否可用
     */
    public static boolean isSDCardMounted() {
        String sDStateString = android.os.Environment.getExternalStorageState();
        if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * sdcard是否只读
     */
    public static boolean isSDCardMountedReadOnly() {
        String sDStateString = android.os.Environment.getExternalStorageState();
        if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED_READ_ONLY)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取SDCard总大小和剩余大小,单位MB
     *
     * @return
     */
    public static HashMap<String, Long> getSDCardSizeInfo() {

        HashMap<String, Long> map = new HashMap<String, Long>();

        String sDcString = android.os.Environment.getExternalStorageState();
        if (sDcString.equals(android.os.Environment.MEDIA_MOUNTED)) {
            // 取得sdcard文件路径
            File pathFile = android.os.Environment.getExternalStorageDirectory();
            android.os.StatFs statfs = new android.os.StatFs(pathFile.getPath());
            // 获取SDCard上BLOCK总数
            long nTotalBlocks = statfs.getBlockCount();
            // 获取SDCard上每个block的SIZE
            long nBlocSize = statfs.getBlockSize();
            // 获取可供程序使用的Block的数量
            long nAvailaBlock = statfs.getAvailableBlocks();
            // 获取剩下的所有Block的数量(包括预留的一般程序无法使用的块)
//			long nFreeBlock = statfs.getFreeBlocks();
            // 计算SDCard 总容量大小MB
            long nSDTotalSize = nTotalBlocks*nBlocSize/1024/1024;
            // 计算 SDCard 剩余大小MB
            long nSDFreeSize = nAvailaBlock*nBlocSize/1024/1024;

            map.put("totalsize", nSDTotalSize);
            map.put("freesize", nSDFreeSize);
        }
        return map;

    }
}
