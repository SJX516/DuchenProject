package com.duchen.template.concept.model;

import com.duchen.template.utils.LogUtil;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 检查数据模型有效性的数据模型解析类
 *
 */
public class LegalModelParser extends ModelParser {

    private static final String TAG = "LegalModelParser";

    /**
     * @param model
     * @return
     */
    private <T> T check(T model) {
        if (model instanceof List) {
            return checkList(model);
        } else {
            return checkObject(model, true);
        }
    }

    /**
     * @param model list
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T> T checkList(T model) {
        List<Object> removed = new ArrayList<Object>();
        List<?> list = (List<?>) model;
        for (Object obj : list) {
            if (checkObject(obj, false) == null) {
                LogUtil.w(LogUtil.TAG_MODEL_CHECK, obj.getClass().getName() + " ilegal model data");
                removed.add(obj);
            }
        }
        list.removeAll(removed);
        return (T) list;
    }

    /**
     * @param obj
     * @return
     */
    private <T> T checkObject(T obj, boolean exception) {
        if (obj == null) return null;
        if (obj instanceof LegalModel) {
            if (!((LegalModel) obj).check()) {
                if (exception) {
                    throw new IllegalModelException(obj.getClass().getName() + " ilegal model data");
                } else {
                    return null;
                }
            }
        } else {
            LogUtil.w(LogUtil.TAG_MODEL_CHECK, obj.getClass().getName() + " IS NOT implement LegalModel");
        }

        return obj;
    }

    // ------------------------------------
    @Override
    public <T> T fromJson(final String json, Class<T> classOfT) {
        T t = null;
        try {
            t = check(super.fromJson(json, classOfT));
        } catch (IllegalModelException e) {
            LogUtil.e(TAG, e.getMessage());
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return t;
    }

    @Override
    public <T> T fromJson(String json, Type typeOfT) {
        T t = null;
        try {
            T model = super.fromJson(json, typeOfT);
            t = check(model);
        } catch (IllegalModelException e) {
            LogUtil.e(TAG, e.getMessage());
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return t;
    }

    @Override
    public <T> T fromJson(JsonElement jsonElement, Type type) {
        T t = null;
        try {
            if (jsonElement == null || jsonElement.isJsonNull()) return null;
            T model = super.fromJson(jsonElement, type);
            t = check(model);
        } catch (IllegalModelException e) {
            LogUtil.e(TAG, e.getMessage());
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return t;
    }
}
