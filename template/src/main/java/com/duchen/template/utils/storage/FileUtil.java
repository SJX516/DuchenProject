package com.duchen.template.utils.storage;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.duchen.template.utils.DLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * **************************文件处理工具类**************************<br>
 * 获取文件大小,获取文件夹大小<br>
 * 格式化文件大小<br>
 * 判断文件是否存在<br>
 * 根据指定文件过期时间删除临时文件<br>
 * 创建文件夹 如果原来文件存在,先删除<br>
 * 删除指定文件<br>
 * 读取指定文件的数据<br>
 * 重命名<br>
 * 拷贝文件<br>
 * 判断两个文件或文件夹是否是同一个<br>
 * 获取Android系统中所有的可写sdcard<br>
 */
public class FileUtil {

    private final static String TAG = "FileUtil";

    /**
     * 获取文件大小
     */
    public static long getFileSizes(File f) throws Exception {// 取得文件大小
        long s = 0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s = fis.available();
            fis.close();
        } else {
            f.createNewFile();
            DLog.e(TAG, "文件不存!");
        }
        return s;
    }

    /**
     * 获取文件夹大小
     */
    public static long getFileSize(File f) throws Exception// 取得文件夹大小
    {
        long size = 0;
        if (!f.exists()) return size;
        if (f.isDirectory()) {
            File flist[] = f.listFiles();
            for (int i = 0; i < flist.length; i++) {
                size = size + getFileSize(flist[i]);
            }
        } else {
            size = size + f.length();
        }
        return size;
    }

    /**
     * 格式化文件大小
     */
    public static String formatFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.0");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = "0K";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS/1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS/1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS/1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 判断文件是否存在
     */
    public static boolean isFileExits(String filename) {
        boolean s = false;
        try {
            File file = new File(filename);
            s = file.exists();
        } catch (Exception e) {
            DLog.e(TAG, e.getMessage());
        }
        return s;
    }

    /**
     * 根据uri地址判断文件是否存在
     */
    public static boolean isFileExists(Uri uri) {
        if (uri == null) return false;
        String uriString = uri.toString();
        if (uriString == null || uriString.length() < 8) return false;
        return isFileExits(uriString.substring(7));
    }

    /**
     * 根据指定文件过期时间删除临时文件
     */
    public static boolean autoDeleteCacheFile(File file, long expiredDuration) {
        if (StorageUtil.isSDCardMounted()) {
            if (file.exists() && file.isDirectory()) {
                try {
                    File[] files = file.listFiles();
                    for (int i = 0; files != null && i < files.length; i++) {
                        if (files[i].isFile() && System.currentTimeMillis() - files[i].lastModified() >=
                                expiredDuration) {
                            files[i].delete();
                        }
                    }
                } catch (Exception e) {
                    DLog.e(TAG, e.getMessage());
                    return false;
                }
                return true;
            } else {
                return true;
            }
        } else return false;

    }

    /**
     * 创建文件夹
     */
    public static boolean createFolder(String filepath) {

        File file = new File(filepath);

        if ((StorageUtil.isSDCardMounted() || StorageUtil.isSDCardMountedReadOnly()) && file.exists()) {
            return true;
        } else {
            if (StorageUtil.isSDCardMounted()) {
                return file.mkdirs();
            } else return false;

        }
    }

    /**
     * 删除指定文件
     */
    public static void delFile(String strFileName) {
        if (StorageUtil.isSDCardMounted()) {
            try {
                File myFile = new File(strFileName);
                if (myFile.exists()) {
                    myFile.delete();
                }
            } catch (Exception e) {
                DLog.e(TAG, e.getMessage());
            }
        }

    }

    /**
     * 读取指定文件的数据
     */
    public static String readDataFromFile(String filepath, String filename) {

        String realfilename = filepath + filename;
        if (StorageUtil.isSDCardMounted() || StorageUtil.isSDCardMountedReadOnly()) {
            if (isFileExits(realfilename)) {

                StringBuffer sb = new StringBuffer();
                FileInputStream fi = null;
                InputStreamReader isr = null;
                BufferedReader br = null;

                try {
                    fi = new FileInputStream(realfilename);
                    isr = new InputStreamReader(fi);
                    br = new BufferedReader(isr);
                    String line = "";
                    while ((line = br.readLine()) != null) {

                        sb.append(line);
                    }

                } catch (FileNotFoundException e) {
                    DLog.e(TAG, e.getMessage());

                } catch (IOException e) {
                    DLog.e(TAG, e.getMessage());
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            DLog.e(TAG, e.getMessage());
                        }
                    }
                }

                return sb.toString();

            } else {
                return "";
            }

        } else {
            return "";
        }
    }

    /**
     * 删除目录或者文件
     */
    public static void delete(String path) {
        delete(new File(path));
    }

    /**
     * 删除目录或者文件
     */
    public static void delete(File file) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                File[] subs = file.listFiles();
                if (subs != null) {
                    for (File sub : subs) {
                        delete(sub.toString());
                    }
                }
            } else {
                deleteFile(file);
            }
        }
    }

    /**
     * 删除单个文件
     */
    public static void deleteFile(String path) {
        if (TextUtils.isEmpty(path)) return;
        delete(new File(path));
    }

    /**
     * 删除单个文件
     */
    public static void deleteFile(File file) {
        if (file != null && file.exists()) {
            file.delete();
        }
    }

    /**
     * 创建文件<br/>
     * 如果原来文件存在,先删除
     */
    public static void createFile(String path) {
        createFile(new File(path));

    }

    /**
     * 创建文件<br/>
     * 如果原来文件存在,先删除
     */
    public static void createFile(File file) {
        if (file.exists()) {
            file.delete();
        }

        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            DLog.e(TAG, e.getMessage());
        }

    }

    /**
     * 重命名
     */
    public static boolean renameFile(String newName, File file) {
        if (file != null && file.exists()) {
            File newFile = new File(newName);
            if (newFile.exists()) {
                newFile.delete();
            }

            return file.renameTo(newFile);
        }
        return false;
    }

    /**
     * 拷贝文件
     */
    public static boolean copyFile(File srcFile, File destFile) {
        boolean result = false;
        try {
            InputStream in = new FileInputStream(srcFile);
            try {
                result = copyToFile(in, destFile);
            } finally {
                in.close();
            }
        } catch (IOException e) {
            result = false;
        }
        return result;
    }

    /**
     * 拷贝文件
     */
    public static boolean copyToFile(InputStream inputStream, File destFile) {
        try {
            if (destFile.exists()) {
                destFile.delete();
            }
            OutputStream out = new FileOutputStream(destFile);
            try {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) >= 0) {
                    out.write(buffer, 0, bytesRead);
                }
            } finally {
                out.close();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 判断两个文件或文件夹是否是同一个
     */
    public static boolean isSameDir(File f1, File f2) {
        if (f1 != null && f1.equals(f2)) return true;
        if (f1 == null || f2 == null) return false;
        if (f1.equals(f2)) {
            return true;
        } else {
            File[] childFiles1 = f1.listFiles();
            File[] childFiles2 = f2.listFiles();
            if (childFiles1 == childFiles2) return true;
            if (childFiles1 == null || childFiles2 == null) return false;
            if (childFiles1.length == childFiles2.length) {
                for (int i = 0; i < childFiles1.length; i++) {
                    if (!childFiles1[i].getName().equals(childFiles2[i].getName())) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 获取Android系统中所有的可写sdcard
     */
    public static List<File> getWritableSDs(Context context) throws InterruptedException {
        List<File> writableFiles = new ArrayList<File>();
        // 读取所有SD卡信息
        HashSet<String> set = getExternalMounts2(context);
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String path = it.next();
            writableFiles.add(new File(path));
        }
        // mnt下没有或者只有一个sd卡
        if (writableFiles.size() == 0) {
            File f = Environment.getExternalStorageDirectory();
            writableFiles.clear();
            writableFiles.add(f);
        }
        return writableFiles;
    }

    /**
     * 通过反射调用android 19新加的getExternalFilesDirs方法获取所有挂载sdcard路径
     */
    private static boolean addExtraSdcards(Context context, HashSet<String> out, String exclude) {
        boolean ret = false;
        try {
            int version = android.os.Build.VERSION.SDK_INT;
            if (version < 19) {
                return ret;
            }

            if (exclude != null && !exclude.endsWith("/")) {
                exclude += "/";
            }

            Method method = Context.class.getMethod("getExternalFilesDirs", String.class);
            Object param = new String[]{null};
            File[] list = (File[]) method.invoke(context, param);
            if (list != null && list.length > 0) {
                for (File file : list) {
                    if (exclude != null && file.getPath().startsWith(exclude)) {
                    } else {
                        String path = null;
                        File parent = file.getParentFile();
                        if (parent != null && parent.canWrite()) {
                            path = parent.getPath();
                        } else {
                            path = file.getPath();
                        }

                        out.add(path);
                    }
                }
            }

            ret = true;
        } catch (Exception e) {
            DLog.e(TAG, e.getMessage());
        }

        return ret;
    }

    public static HashSet<String> getExternalMounts2(Context context) throws InterruptedException {
        final HashSet<String> out = new HashSet<String>();

        String path = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getPath();
            out.add(path);
        }

        if (addExtraSdcards(context, out, path)) {
            return out;
        }
        try {
            String reg = ".* (vfat|ntfs|exfat|fat32|fuse) .*rw.*";
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
        } catch (IOException e) {
            DLog.e(TAG, e.getMessage());
        }
        return out;
    }

    /**
     * 读取assert下的文本文件
     */
    public static String readStringFromAssertFile(Context context, String fileName) {
        String content = "";
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            content = new String(buffer);
        } catch (Exception e) {
            DLog.e(TAG, e.getMessage());
        }
        return content;
    }
}
