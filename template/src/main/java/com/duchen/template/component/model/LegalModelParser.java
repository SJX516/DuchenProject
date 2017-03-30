package com.duchen.template.component.model;

import com.duchen.template.utils.LogUtil;
import com.duchen.template.utils.datahelper.DataCheckUtil;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;

/**
 * 检查数据模型有效性的数据模型解析类
 */
public class LegalModelParser extends ModelParser {

    private static final String TAG = "LegalModelParser";

    @Override
    public <T> T fromJson(final String json, Class<T> classOfT) {
        T t;
        t = super.fromJson(json, classOfT);
        if (DataCheckUtil.check(t)) {
            return t;
        } else {
            LogUtil.d(TAG, t.getClass().getName() + " data check failed, detail json : " + json);
            return null;
        }
    }

    @Override
    public <T> T fromJson(String json, Type typeOfT) {
        T t;
        t = super.fromJson(json, typeOfT);
        if (DataCheckUtil.check(t)) {
            return t;
        } else {
            LogUtil.d(TAG, t.getClass().getName() + " data check failed, detail json : " + json);
            return null;
        }
    }

    @Override
    public <T> T fromJson(JsonElement jsonElement, Type type) {
        T t;
        if (jsonElement == null || jsonElement.isJsonNull()) return null;
        t = super.fromJson(jsonElement, type);
        if (DataCheckUtil.check(t)) {
            return t;
        } else {
            LogUtil.d(TAG, t.getClass().getName() + " data check failed, detail json : " + jsonElement.toString());
            return null;
        }
    }
}
