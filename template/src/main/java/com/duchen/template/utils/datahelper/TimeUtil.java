package com.duchen.template.utils.datahelper;

import android.text.TextUtils;

import com.duchen.template.utils.LogUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
    private static final String TAG = "TimeUtil";

    private static final String DATE_FORMAT_IN = "EEE MMM dd HH:mm:ss Z yyyy";
    private static final String DATE_FORMAT_OUT = "yyyy-MM-dd";
    private static SimpleDateFormat mInputDateFormat = null;
    private static SimpleDateFormat mOutDateFormat = null;

    public static final long MS_DAY = 86400000l;
    public static final long MS_WEEK = 604800000l;

    static final long DAY = 24*60*60;
    static final long HOUR = 60*60;
    static final long MINUTE = 60;

    public static String formatTime(long timestamp) {
        final long now = System.currentTimeMillis();
        if (timestamp > 0 && now > timestamp) {
            long timeGap = (now - timestamp)/1000;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String[] stampStrs = sdf.format(new Date(timestamp)).split("-");
            String[] nowStrs = sdf.format(new Date(now)).split("-");
            boolean sameYear = stampStrs[0].equalsIgnoreCase(nowStrs[0]);
            boolean sameDay = stampStrs[2].equalsIgnoreCase(nowStrs[2]);
            timeGap = timeGap > 0 ? timeGap : 0;
            if (timeGap < MINUTE) {
                return timeGap + "秒前";
            } else if (timeGap < HOUR) {
                return timeGap/60 + "分钟前";
            } else if (timeGap < DAY) {
                if (sameDay) {
                    sdf = new SimpleDateFormat("HH:mm");
                } else {
                    sdf = new SimpleDateFormat("昨天HH:mm");
                }
                return sdf.format(new Date(timestamp));
            } else if (sameYear) {
                sdf = new SimpleDateFormat("MM月dd日");
                return sdf.format(new Date(timestamp));
            } else {
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.format(new Date(timestamp));
            }
        }
        return "";
    }

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

    /**
     * 格式化ms毫秒成 yyyy-MM-dd
     */
    public static String formatMS2YMD(long ms) {
        return getOutDateFormat().format(ms);
    }

    /**
     * 格式化ms毫秒成 yyyy-MM-dd HH:mm:ss
     */
    public static String formatMS2YMDHMS(long ms) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(ms);
    }

    /**
     * 格式化ms毫秒成 自定义时间格式
     */
    public static String formatMS2CustomFormat(long ms, String format) {
        if (TextUtils.isEmpty(format)) return "";
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(ms);
    }

    /**
     * 格式化Data.toString时间成ms毫秒
     */
    public static long formatDate2MS(String dateCreated) {
        Date dateRes = null;
        if (StringUtil.nullStr(dateCreated) == null) return 0;

        try {
            dateRes = getInputDateFormat().parse(dateCreated);
        } catch (ParseException e) {
            LogUtil.e(TAG, e.getMessage());
        }
        if (dateRes == null) return 0;
        else return dateRes.getTime();
    }

    /**
     * 根据ms毫秒时间计算天数
     */
    public static int ms2Day(long millisecond) {
        if (millisecond <= 0) return 0;
        final long DAY = 1000*60*60*24;
        int day = (int) (millisecond/DAY);
        if (millisecond%DAY != 0) {
            day++;
        }
        return day;
    }

    /**
     * 根据ms毫秒时间计算星期数
     */
    public static int ms2week(long millisecond) {
        int day = ms2Day(millisecond);
        int week = day/7;
        if (day%7 != 0) {
            week++;
        }
        return week;
    }

    /**
     * 判断两个日期是否在同一周
     */
    public static boolean isSameWeekDates(Date date1, Date date2) {
        if (date1 == null || date2 == null) return false;

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) return true;
        } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) return true;
        } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) return true;
        }
        return false;
    }

    /**
     * 根据日期获得星期
     */
    public static String getWeekOfDate(Date date) {
        if (date == null) return "";

        String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        // String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    public static long getCurrentTime() {
        long current = System.currentTimeMillis();
        return current;
    }
}
