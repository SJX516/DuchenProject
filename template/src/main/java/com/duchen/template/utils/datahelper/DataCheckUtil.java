package com.duchen.template.utils.datahelper;

import android.support.annotation.NonNull;

import com.duchen.template.concept.model.LegalModel;
import com.duchen.template.utils.LogUtil;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 数据检查类
 */
public class DataCheckUtil {

    private static final String TAG = "DataCheckUtils";

    /**
     * 检查NonNull注解的成员变量
     * 注意：只能检测类本身的成员变量，不能检测从基类继承而来的变量
     *
     * @param classType
     * @param object
     * @return true--检查通过；false--检查失败
     */
    public static boolean checkFieldsNotNullAnnotation(Class classType, Object object) {
        if (classType == null || object == null) return false;
        Field[] fields = classType.getDeclaredFields();
        if (fields == null) return true;
        for (Field field : fields) {
            field.setAccessible(true);
            field.getDeclaredAnnotations();
            if (field.getAnnotation(NonNull.class) != null) {
                try {
                    if (field.get(object) == null) {
                        LogUtil.e(TAG, "Data check failed " + classType.getName() + "." + field.getName() + " is null");
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    LogUtil.e(TAG, e.getMessage());
                    return false;
                } catch (IllegalArgumentException e) {
                    LogUtil.e(TAG, e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkListDataUsable(List<?> list) {
        if (list == null || list.size() == 0) {
            return false;
        } else {
            for (Object object : list) {
                if (object == null) {
                    return false;
                } else if (object instanceof LegalModel && !((LegalModel)object).check()) {
                    return false;
                }
            }
            return true;
        }
    }
}
