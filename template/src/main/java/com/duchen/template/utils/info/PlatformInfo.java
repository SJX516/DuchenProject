package com.duchen.template.utils.info;

import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PlatformInfo {

    private String system;
    private String versionName;
    private int versionCode;
    private List<String> supportAbis;

    public PlatformInfo() {
        system = "Android";
        versionName = Build.VERSION.RELEASE;
        versionCode = Build.VERSION.SDK_INT;
        supportAbis = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            supportAbis = Arrays.asList(Build.SUPPORTED_ABIS);
        } else {
            supportAbis.add(Build.CPU_ABI);
            supportAbis.add(Build.CPU_ABI2);
        }
    }

    public JSONObject toJsonObject() throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("system", system);
        jsonObject.put("versionName", versionName);
        jsonObject.put("versionCode", versionCode);
        JSONArray abis = new JSONArray();
        abis.put(supportAbis);
        jsonObject.put("supportAbis", abis);
        return jsonObject;
    }
}
