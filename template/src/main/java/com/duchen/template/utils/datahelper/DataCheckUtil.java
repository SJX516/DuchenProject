package com.duchen.template.utils.datahelper;

import android.support.annotation.NonNull;

import com.duchen.template.component.model.LegalModel;
import com.duchen.template.utils.LogUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
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

    public static <T> boolean check(T model) {
        if (model instanceof List) {
            return checkList(model);
        } else {
            return checkObject(model);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> boolean checkList(T model) {
        List<Object> removed = new ArrayList<>();
        List<?> list = (List<?>) model;
        for (Object obj : list) {
            if (!checkObject(obj)) {
                removed.add(obj);
            }
        }
        list.removeAll(removed);
        return list.size() != 0;
    }

    private static <T> boolean checkObject(T obj) {
        if (obj == null) return false;
        if (obj instanceof LegalModel) {
            return ((LegalModel) obj).check();
        }
        return true;
    }

}
