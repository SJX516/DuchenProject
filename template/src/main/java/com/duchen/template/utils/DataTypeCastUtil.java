package com.duchen.template.utils;

/**
 * 数据类型转换帮助类
 */
public class DataTypeCastUtil {

    private static final String TAG = "DataTypeCastUtils";

    // string  ->  long
    public static long string2long(String string) {
        try {
            return Long.valueOf(string);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        } catch (Error e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return 0;
    }

    // string  ->  float
    public static float string2float(String string) {
        try {
            return Float.valueOf(string);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        } catch (Error e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return 0f;
    }

    // string  ->  int
    public static int string2int(String string) {
        try {
            return Integer.valueOf(string);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        } catch (Error e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return 0;
    }

    public static long primitive(Long value) {
        if (value == null) {
            return 0;
        }
        return value;
    }

    public static int primitive(Integer value) {
        if (value == null) {
            return 0;
        }
        return value;
    }
}
