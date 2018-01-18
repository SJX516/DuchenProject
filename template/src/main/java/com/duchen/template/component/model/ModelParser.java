package com.duchen.template.component.model;

import com.duchen.template.utils.DLog;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;

/**
 * 数据模型解析类
 */
public class ModelParser {

    private static final String TAG = "ModelParser";

    protected Gson mGson;

    public ModelParser() {
        mGson = new Gson();
    }

    public <T> T fromJson(final String json, Class<T> classOfT) {
        T t = null;
        try {
            if (json == null) return null;
            t = mGson.fromJson(json, classOfT);
        } catch (Exception e) {
            DLog.e(TAG, e.getMessage());
        }
        return t;
    }

    public <T> T fromJson(String json, Type typeOfT) {
        T t = null;
        try {
            t = mGson.fromJson(json, typeOfT);
        } catch (Exception e) {
            DLog.e(TAG, e.getMessage());
        }
        return t;
    }

    public <T> T fromJson(JsonElement jsonElement, Type type) {
        T t = null;
        try {
            t = mGson.fromJson(jsonElement, type);
        } catch (Exception e) {
            DLog.e(TAG, e.getMessage());
        }
        return t;
    }

    public String toJson(Object src) {
        String jsonString = null;
        try {
            jsonString = mGson.toJson(src);
        } catch (Exception e) {
            DLog.e(TAG, e.getMessage());
        }
        return jsonString;
    }

    public String toJson(Object src, Type typeOfSrc) {
        String jsonString = null;
        try {
            jsonString = mGson.toJson(src, typeOfSrc);
        } catch (Exception e) {
            DLog.e(TAG, e.getMessage());
        }
        return jsonString;
    }

    public String toJson(JsonElement jsonElement) {
        String jsonString = null;
        try {
            jsonString = mGson.toJson(jsonElement);
        } catch (Exception e) {
            DLog.e(TAG, e.getMessage());
        }
        return jsonString;
    }
}
