package com.duchen.template.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;

import com.duchen.template.component.BaseApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * **************************framework全局工具类**************************<br>
 * 提取替换Url参数<br>
 * 获取CDN链接<br>
 * 获取指定文件大小,删除全部文件<br>
 * 提取标准的argb色值为整数数组<br>
 * 检测输入的MD5值<br>
 * 移除url地址中拼接的参数<br>
 * 判断当前进程是否在主进程中<br>
 * 对原文本中的子文本进行高亮显示<br>
 * 获取屏幕顶部statusbar的高度 单位px<br>
 * 判断是否是全中文or英文字符<br>
 * 打开关闭系统软键盘<br>
 * 格式化float保留两位小数<br>
 */
public class Util {
    private static final String TAG = "Util";
    static byte[] GUID = {(byte) 0xf6, 0x56, (byte) 0xc1, (byte) 0xc5, 0x6c, (byte) 0xbd, 0x46, (byte) 0xc0, (byte)
            0xbd, 0x37, (byte) 0x9b, (byte) 0xe5, 0x36, (byte) 0xc8, (byte) 0xdb, 0x00};

    private static final String DATE_FORMAT_IN = "EEE MMM dd HH:mm:ss Z yyyy";
    private static final String DATE_FORMAT_OUT = "yyyy-MM-dd";
    private static SimpleDateFormat mInputDateFormat = null;
    private static SimpleDateFormat mOutDateFormat = null;
    public static String swVersion = null;

    private static DateFormat getInputDateFormat() {
        if (mInputDateFormat == null) {
            mInputDateFormat = new SimpleDateFormat(DATE_FORMAT_IN, Locale.US);
        }
        return mInputDateFormat;
    }

    private static DateFormat getOutDateFormat() {
        if (mOutDateFormat == null) {
            mOutDateFormat = new SimpleDateFormat(DATE_FORMAT_OUT, Locale.CHINA);
        }
        return mOutDateFormat;
    }

    public static boolean isArrivedLimit(int count, int base, int mod) {
        if (count <= 0 || base <= 0 || mod <= 0) {
            return false;
        }

        if (count >= base) {
            if (0 == (count - base)%mod) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String nullStr(String str) {
        if (TextUtils.isEmpty(str) || str.equalsIgnoreCase("null")) return null;

        return str;

    }

    /**
     * 获取一个非空的String
     */
    public static String getNotNullString(String str) {
        if (str == null) return "";
        return str;
    }

    public static int parseInt(String str) {
        int result = 0;
        try {
            result = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return result;
    }

    public static float parseFloat(String str) {
        float result = 0;
        try {
            result = Float.parseFloat(str);
        } catch (NumberFormatException e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return result;
    }

    public static long parseLong(String str) {
        long result = 0;
        try {
            result = Long.parseLong(str);
        } catch (NumberFormatException e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return result;
    }

    public static String formatDateYMD(String dateCreated) {
        Date dateRes = new Date();
        if (nullStr(dateCreated) == null) return getOutDateFormat().format(dateRes);

        try {
            dateRes = getInputDateFormat().parse(dateCreated);
        } catch (ParseException e) {
            LogUtil.e(TAG, e.getMessage());
        }

        if (dateRes == null) return dateCreated;
        else {
            return getOutDateFormat().format(dateRes);
        }
    }

    public static String formatMS2YMD(long ms) {
        return getOutDateFormat().format(ms);
    }

    public static String formatMS2YMDHMS(long ms) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(ms);
    }

    public static String formatMS2CustomFormat(long ms, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(ms);
    }

    public static long formatDate2MS(String dateCreated) {
        Date dateRes = null;
        if (nullStr(dateCreated) == null) return 0;

        try {
            dateRes = getInputDateFormat().parse(dateCreated);
        } catch (ParseException e) {
            LogUtil.e(TAG, e.getMessage());
        }
        if (dateRes == null) return 0;
        else return dateRes.getTime();
    }

    static final String URLCharTable = "!#$%&'()*+,-./:;=?@[\\]^_`{|}~";

    public static String getHttpLink(String str, int offset) {
        int len = 0;
        if (Util.startsWithIgnoreCase(str, offset, "http://")) {
            len = "http://".length();
        } else if (Util.startsWithIgnoreCase(str, offset, "www.")) {
            len = "www.".length();
        } else if (Util.startsWithIgnoreCase(str, offset, "wap.")) {
            len = "wap.".length();
        } else if (Util.startsWithIgnoreCase(str, offset, "https://")) {
            len = "https://".length();
        } else {
            return null;
        }

        int strLen = str.length();

        while (offset + len < strLen) {
            char c = str.charAt(offset + len);
            if ((c >= 'A' && c <= 'Z') // 'a' - 'z'
                    || (c >= 'a' && c <= 'z') // 'A' - 'Z'
                    || (c >= '0' && c <= '9')) { // '0' - '9'
                len++;
            } else {
                if (URLCharTable.indexOf(c) >= 0) {
                    len++;
                } else {
                    break;
                }
            }
        }

        return str.substring(offset, offset + len);
    }

    public static int ms2Day(long millisecond) {
        if (millisecond <= 0) return 0;
        final long DAY = 1000*60*60*24;
        int day = (int) (millisecond/DAY);
        if (millisecond%DAY != 0) {
            day++;
        }
        return day;
    }

    public static int ms2week(long millisecond) {
        int day = ms2Day(millisecond);
        int week = day/7;
        if (day%7 != 0) {
            week++;
        }
        return week;
    }

    public static String getStringEraseSuffix(String str) {
        if (str != null && str.length() > 0) {
            int loc = str.lastIndexOf(".");
            if (loc > 0 && loc < str.length()) {
                String suffix = str.substring(loc + 1);
                if (suffix.matches("[a-zA-Z]*")) {
                    str = str.substring(0, loc);
                }
            }
        }
        return str;
    }

    public static boolean startsWithIgnoreCase(String str, int offset, String anObject) {
        int length = anObject.length();

        if (offset + length > str.length()) {
            return false;
        }

        int idx = 0;

        while (idx < length) {
            char c = str.charAt(offset + idx);
            if (c >= 'A' && c <= 'Z') {
                c += 32;
            }
            if (c != anObject.charAt(idx)) {
                break;
            } else {
                idx++;
            }
        }
        if (idx == length && idx > 0) {
            return true;
        }
        return false;
    }

    public static String toString(String str) {
        return str == null ? "" : str;
    }

    public static boolean isStringEmpty(String v) {
        if (v == null || v.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 字符串是否有意义。
     *
     * @param string
     * @return
     */
    public static boolean isBlankString(String string) {
        if (string == null || string.length() == 0) return true;

        int l = string.length();
        for (int i = 0; i < l; i++) {
            if (!Util.isWhitespace(string.codePointAt(i))) return false;
        }
        return true;
    }

    public static boolean isWhitespace(int c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\f' || c == '\r';
    }

    /**
     * 替换Url的参数
     *
     * @param originUrl  url原字符串
     * @param paramKey   参数的key
     * @param paramValue 替换的value
     * @return 替换后的url
     */
    public static String replaceOrAppendUriQueryParamValue(final String originUrl, final String paramKey, final
    String paramValue) {
        if (TextUtils.isEmpty(originUrl)) return originUrl;
        if (TextUtils.isEmpty(paramKey)) return originUrl;
        Uri uri = Uri.parse(originUrl);

        final Set<String> params = uri.getQueryParameterNames();
        Uri.Builder newUri;
        if (params.contains(paramKey)) {
            newUri = uri.buildUpon().clearQuery();
            for (String param : params) {
                String value;
                if (param.equals(paramKey)) {
                    value = paramValue;
                } else {
                    value = uri.getQueryParameter(param);
                }
                newUri.appendQueryParameter(param, value);
            }
        } else {
            newUri = uri.buildUpon();
            newUri.appendQueryParameter(paramKey, paramValue);
        }

        return newUri.toString();
    }

    /**
     * 直接追加Url的参数
     *
     * @param originUrl  url原字符串
     * @param paramKey   参数的key
     * @param paramValue 替换的value
     * @return 替换后的url
     */
    public static String appendUriQueryParamValue(final String originUrl, final String paramKey, final String
            paramValue) {
        if (TextUtils.isEmpty(originUrl)) return originUrl;
        if (TextUtils.isEmpty(paramKey)) return originUrl;
        if (TextUtils.isEmpty(paramValue)) return originUrl;
        Uri uri = Uri.parse(originUrl);

        Uri.Builder newUri;
        newUri = uri.buildUpon();
        newUri.appendQueryParameter(paramKey, paramValue);

        return newUri.toString();
    }

    public static String getCDNUrl(String originalUri, String CDNHost) {
        if (TextUtils.isEmpty(originalUri) || TextUtils.isEmpty(CDNHost)) {
            return originalUri;
        }
        Uri uri = Uri.parse(originalUri);
        String schema = uri.getScheme();
        String path = uri.getPath();
        String query = uri.getQuery();
        String host = uri.getHost();
        String newUrl = schema + "://" + CDNHost + "/" + host + path;
        if (!TextUtils.isEmpty(query)) {
            newUrl = newUrl + "?" + query;
        }
        return newUrl;
    }

    public static Vector split(String txt, String splitStr) {
        if (txt == null || txt.length() <= 0 || splitStr == null || splitStr.length() <= 0) {

            return null;
        }

        String[] strArr = txt.split(splitStr);
        if (strArr.length > 0) {
            Vector<String> strings = new Vector<String>();
            for (int i = 0; i < strArr.length; i++)
                strings.addElement(strArr[i]);
            return strings;
        }

        return null;
    }

    public static Hashtable cloneHashtable(Hashtable table) {

        if (table != null) {
            return (Hashtable) table.clone();
        } else {
            return null;
        }
    }

    private static final int INSERTIONSORT_THRESHOLD = 7;

    public static void sort(Vector list) {
        if (list != null && list.size() > 0) {
            Object[] src = new Object[list.size()];
            Object[] dest = new Object[list.size()];
            list.copyInto(src);
            list.copyInto(dest);
            mergeSort(src, dest, 0, src.length, 0);
            for (int i = 0; i < dest.length; i++) {
                list.setElementAt(dest[i], i);
            }
        }
    }

    private static void mergeSort(Object[] src, Object[] dest, int low, int high, int off) {
        int length = high - low;

        if (length < INSERTIONSORT_THRESHOLD) {
            for (int i = low; i < high; i++) {
                for (int j = i; j > low && ((Comparable) dest[j - 1]).compareTo(dest[j]) > 0; j--) {
                    swap(dest, j, j - 1);
                }
            }
            return;
        }

        int destLow = low;
        int destHigh = high;
        low += off;
        high += off;
        int mid = (low + high) >> 1;
        mergeSort(dest, src, low, mid, -off);
        mergeSort(dest, src, mid, high, -off);

        if (((Comparable) src[mid - 1]).compareTo(src[mid]) <= 0) {
            System.arraycopy(src, low, dest, destLow, length);
            return;
        }

        for (int i = destLow, p = low, q = mid; i < destHigh; i++) {
            if (q >= high || p < mid && ((Comparable) src[p]).compareTo(src[q]) <= 0) dest[i] = src[p++];
            else dest[i] = src[q++];
        }
    }

    private static void swap(Object[] x, int a, int b) {
        Object t = x[a];
        x[a] = x[b];
        x[b] = t;
    }

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
            LogUtil.e(TAG, e.getMessage());
            result = false;
        }
        return result;
    }

    public static boolean copyToFile(InputStream inputStream, File destFile) {
        try {
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
            LogUtil.e(TAG, e.getMessage());
            return false;
        }
    }

    public static String getSwVersion(Context context) {
        if (swVersion == null || swVersion.length() == 0) swVersion = swVersionStr(context);
        return swVersion;
    }

    public static String swVersionStr(Context context) {
        String str = "";
        try {
            str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            LogUtil.e(TAG, e.getMessage());
        }

        return str;
    }

    /**
     * 将字节数量转换为便于显示的方式
     *
     * @param size 字节数
     */
    public static String getFileSizeString(long size) {
        if (size <= 0) {
            return "0.0B";
        }
        // < 1K
        if (size < 1024) {
            return size + "B";
        }
        // 1K -- 1M
        else if (size >= 1024 && size < 1048576) {
            int countK = (int) (size/1024);
            int smallK = (int) ((size*10/1024)%10);

            if (smallK > 0) {
                return countK + "." + smallK + "KB";
            } else {
                return countK + "KB";
            }
        }
        // > 1M
        else if (size >= 1048576 && size < 1073741824) {
            int countM = (int) (size/(1048576));
            int smallM = (int) ((size*10/(1048576))%10);

            if (smallM > 0) {
                return countM + "." + smallM + "MB";
            } else {
                return countM + "MB";
            }
        } else {
            int countM = (int) (size/(1073741824));
            int smallM = (int) ((size*10/(1073741824))%10);

            if (smallM > 0) {
                return countM + "." + smallM + "GB";
            } else {
                return countM + "GB";
            }
        }
    }

    /**
     * 提取标准的argb色值为整数数组
     */
    public static boolean delAllFiles(String path) {
        File file = new File(path);
        if (file.exists()) {
            path = file.getPath();
            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }

            String tmp_path = path + "_tmp";
            try {
                Runtime.getRuntime().exec(new String[]{"mv", path, tmp_path}).waitFor();
                Runtime.getRuntime().exec(new String[]{"rm", "-r", tmp_path});
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                return false;
            }
        }

        return true;
    }


    public static int[] toRGB(String color) {
        int[] argb = new int[4];
        if (color.length() == 8) {
            argb[0] = Integer.valueOf(color.substring(0, 2), 16);
            argb[1] = Integer.valueOf(color.substring(2, 4), 16);
            argb[2] = Integer.valueOf(color.substring(4, 6), 16);
            argb[3] = Integer.valueOf(color.substring(6, 8), 16);
        }

        return argb;
    }


    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f'};

    public static char[] encodeHex(byte[] data) {
        int l = data.length;

        char[] out = new char[l << 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }

        return out;
    }

    public static boolean checkMD5(InputStream is, String verfyMD5) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] data = new byte[1024*4];
            int length = 0;
            while ((length = is.read(data)) > 0) {
                md.update(data, 0, length);
            }

            is.close();

            BigInteger number = new BigInteger(1, md.digest());
            String md5 = number.toString(16);

            while (md5.length() < 32) md5 = "0" + md5;

            return verfyMD5.equals(md5);

        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            return false;
        }
    }

    public static int dip2px(Context context, float dpValue) {
        if (context == null || context.getResources() == null || context.getResources().getDisplayMetrics() == null)
            return (int) dpValue;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue*scale + 0.5f);
    }

    public static int px2dip(Context context, float dpValue) {
        if (context == null || context.getResources() == null || context.getResources().getDisplayMetrics() == null)
            return (int) dpValue;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue/scale + 0.5f);
    }

    public static boolean isCurrentProcess(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityMag = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String processName = context.getApplicationInfo().processName;
//		LogUtil.i("isCurrentProcess", "Mypid = " +  pid + "|MyprecessName = " + processName);
        for (RunningAppProcessInfo appProcess : activityMag.getRunningAppProcesses()) {
//			LogUtil.i("isCurrentProcess", "pid = " +  appProcess.pid + "|precessName = " + appProcess.processName);
            if (appProcess.pid == pid && appProcess.processName.equalsIgnoreCase(processName)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Cancel an {@link AsyncTask}.  If it's already running, it'll be interrupted.
     */
    public static void cancelTaskInterrupt(AsyncTask<?, ?, ?> task) {
        cancelTask(task, true);
    }

    /**
     * Cancel an {@link AsyncTask}.
     *
     * @param mayInterruptIfRunning <tt>true</tt> if the thread executing this
     *                              task should be interrupted; otherwise, in-progress tasks are allowed
     *                              to complete.
     */
    public static void cancelTask(AsyncTask<?, ?, ?> task, boolean mayInterruptIfRunning) {
        if (task != null && task.getStatus() != AsyncTask.Status.FINISHED) {
            task.cancel(mayInterruptIfRunning);
        }
    }

    /**
     * 通过反射取消动画
     *
     * @param receiver
     */
    public static void cancelAnimation(Object receiver) {
        if (PlatformUtil.getSDKVersionNumber() >= 8) {
            try {
                Method cancel = Animation.class.getMethod("cancel");
                cancel.invoke(receiver);
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
            }
        }
    }

    //判断邮件地址合法性
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-]{1,256}" + "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

    public static boolean isEmailAddress(String s) {
        Matcher match = EMAIL_ADDRESS_PATTERN.matcher(s);
        return match.matches();
    }

    static final char DBC_CHAR_START = 33; // 半角! 
    static final char DBC_CHAR_END = 126; // 半角~
    static final char SBC_CHAR_START = 65281; // 全角！
    static final char SBC_CHAR_END = 65374; // 全角～
    static final int CONVERT_STEP = 65248; // 全角半角转换间隔
    static final char SBC_SPACE = 12288; // 全角空格 12288
    static final char DBC_SPACE = ' '; // 半角空格

    /**
     * zym
     * 半角字符->全角字符转换
     * 只处理空格，!到˜之间的字符，忽略其他
     */
    public static String bj2qj(String src) {
        if (src == null) {
            return src;
        }
        StringBuilder buf = new StringBuilder(src.length());
        char[] ca = src.toCharArray();
        for (int i = 0; i < ca.length; i++) {
            if (ca[i] == DBC_SPACE) { // 如果是半角空格，直接用全角空格替代
                buf.append(SBC_SPACE);
            } else if ((ca[i] >= DBC_CHAR_START) && (ca[i] <= DBC_CHAR_END)) { // 字符是!到~之间的可见字符
                buf.append((char) (ca[i] + CONVERT_STEP));
            } else { // 不对空格以及ascii表中其他可见字符之外的字符做任何处理
                buf.append(ca[i]);
            }
        }
        return buf.toString();
    }

    /**
     * zym
     * 全角字符->半角字符转换
     * 只处理全角的空格，全角！到全角～之间的字符，忽略其他
     */
    public static String qj2bj(String src) {
        if (src == null) {
            return src;
        }
        StringBuilder buf = new StringBuilder(src.length());
        char[] ca = src.toCharArray();
        for (int i = 0; i < src.length(); i++) {
            if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) { // 如果位于全角！到全角～区间内
                buf.append((char) (ca[i] - CONVERT_STEP));
            } else if (ca[i] == SBC_SPACE) { // 如果是全角空格
                buf.append(DBC_SPACE);
            } else { // 不处理全角空格，全角！到全角～区间外的字符
                buf.append(ca[i]);
            }
        }
        return buf.toString();
    }

    public static String removeUriQuery(final String url, final String param) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(param)) return url;
        // ?param&  ---> ?
        String query = "?" + param + "&";
        if (url.contains(query)) return url.replace(param + "&", "");

        // ?param   --->  ""
        query = "?" + param;
        if (url.contains(query)) return url.replace("?" + param, "");

        // &param  ----> ""
        query = "&" + param;
        if (url.contains(query)) return url.replace("&" + param, "");

        return url;
    }

    public static boolean isEnglish(String charaString) {
        return charaString.matches("^[a-zA-Z]*");
    }

    public static boolean isChinese(String str) {
        String regEx = "[\\u4e00-\\u9fa5]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.find()) return true;
        else return false;
    }

    public static String getChineseEnglishMixStringByLength(final String originString, final int length) {
        return getChineseEnglishMixStringByLength(originString, length, false);
    }

    public static String getChineseEnglishMixStringByLength(final String originString, final int length, boolean
            needEllipsis) {
        if (TextUtils.isEmpty(originString)) {
            return originString;
        }

        int count = 0;
        int i = 0;
        while (i < originString.length()) {
            String letter = String.valueOf(originString.charAt(i));
            if (isChinese(letter)) {
                count += 2;
            } else {
                count++;
            }
            i++;
            if (count > length*2) break;
        }

        if (count <= length*2) {
            return originString;
        } else if (needEllipsis) {
            return originString.substring(0, i - 1) + "...";
        }
        return originString.substring(0, i - 1);
    }

    /**
     * 检测网络资源是否存在
     *
     * @param strUrl
     * @return
     */
    public static boolean isNetFileAvailable(String strUrl) {
        InputStream netFileInputStream = null;
        try {
            URL url = new URL(strUrl);
            URLConnection urlConn = url.openConnection();
            netFileInputStream = urlConn.getInputStream();
            if (null != netFileInputStream) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            LogUtil.e(TAG, e.getMessage());
            return false;
        } finally {
            try {
                if (netFileInputStream != null) netFileInputStream.close();
            } catch (IOException e) {
                LogUtil.e(TAG, e.getMessage());
            }
        }
    }

    public static String addHttpProtocol(final String url) {
        if (TextUtils.isEmpty(url)) return "";
        if (!url.contains("://")) {
            return "http://" + url;
        }
        return url;
    }

    public static void showKeyBoard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyBoard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 获取屏幕顶部statusbar的高度 单位px
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 由于加了云信sdk,需要判断是否在主进程中
     */
    public static boolean inMainProcess() {
        String packageName = BaseApplication.getInstance().getPackageName();
        String processName = PlatformUtil.getProcessName(BaseApplication.getInstance());
        return packageName.equals(processName);
    }

    /**
     * 对原文本中的子文本进行高亮显示
     *
     * @param originalString 原文本
     * @param markString     高亮子文本
     * @param highlightColor 高亮颜色值 必须是具体的颜色值 不可以是颜色id
     * @param allWithColor   是否所有子文本都进行高亮
     */
    public static SpannableString getStringWithColor(String originalString, String markString, int highlightColor,
                                                     boolean allWithColor) {
        SpannableString spanString = new SpannableString(originalString);
        if (!TextUtils.isEmpty(markString)) {
            int start = 0;
            // 这里进行高亮显示关键字
            while (start < originalString.length()) {
                int wordStart = originalString.toLowerCase().indexOf(markString.toLowerCase(), start);
                if (wordStart == -1) {
                    break;
                }
                int wordEnd = wordStart + markString.length();
                spanString.setSpan(new ForegroundColorSpan(highlightColor), wordStart, wordEnd, Spannable
                        .SPAN_INCLUSIVE_INCLUSIVE);
                if (!allWithColor) {
                    break;
                }
                start = wordEnd;
            }
        }
        return spanString;
    }

    /**
     * 数组中在尾部添加元素
     *
     * @param arr     原数组
     * @param element 添加元素
     * @return 添加后数组
     */
    public static Object[] arrayAppendElement(Object[] arr, Object element) {
        final int N = arr.length;
        arr = Arrays.copyOf(arr, N + 1);
        arr[N] = element;
        return arr;
    }

    /**
     * returns the bytesize of the give bitmap
     */
    public static int byteSizeOf(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        } else {
            return bitmap.getRowBytes()*bitmap.getHeight();
        }
    }

    public static int versionCodeCompare(String currentVersion, String targerVersion) {
        if (TextUtils.isEmpty(currentVersion) || TextUtils.isEmpty(targerVersion)) {
            return 0;
        }
        int[] current = converVersionCodeToIntArray(currentVersion);
        int[] target = converVersionCodeToIntArray(targerVersion);
        if (current == null || current.length < 3 || target == null || target.length < 3) {
            return 0;
        }
        if (current[0] > target[0]) {
            return 1;
        } else if (current[0] < target[0]) {
            return -1;
        }
        if (current[1] > target[1]) {
            return 1;
        } else if (current[1] < target[1]) {
            return -1;
        }
        if (current[2] > target[2]) {
            return 1;
        } else if (current[2] < target[2]) {
            return -1;
        }
        return 0;
    }

    private static int[] converVersionCodeToIntArray(String versionCode) {
        int[] reslut = {0, 0, 0};
        if (TextUtils.isEmpty(versionCode)) {
            return reslut;
        }
        String[] version = versionCode.split("\\.");
        if (version == null || version.length < 3) {
            return reslut;
        }
        reslut[0] = parseInt(version[0]);
        reslut[1] = parseInt(version[1]);
        reslut[2] = parseInt(version[2]);
        return reslut;
    }

}
