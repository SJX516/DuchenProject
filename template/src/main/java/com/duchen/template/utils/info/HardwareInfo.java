package com.duchen.template.utils.info;

import android.os.Build;

import org.json.JSONException;
import org.json.JSONObject;

public class HardwareInfo {

    private String manufacturer;
    private String type;
    private String serial;
    private String version;
    private String fingerprint;

    public HardwareInfo() {
        manufacturer = Build.MANUFACTURER;
        type = Build.MODEL;
        serial = Build.SERIAL;
        version = Build.DISPLAY;
        fingerprint = Build.FINGERPRINT;
    }

    public JSONObject toJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("manufacturer", manufacturer);
        jsonObject.put("type", type);
        jsonObject.put("serial", serial);
        jsonObject.put("version", version);
        jsonObject.put("fingerprint", fingerprint);
        return jsonObject;
    }
}
