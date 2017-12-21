package com.duchen.template.utils.info;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.duchen.template.component.ApplicationBase;
import com.duchen.template.utils.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class CustomNetworkInfo {


    private String type;
    private String subType;
    private boolean isConnected;
    private boolean isFailover;
    private boolean isRoaming;
    private String ip;

    public CustomNetworkInfo() {
        ConnectivityManager cm = (ConnectivityManager) ApplicationBase.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        type = info.getTypeName();
        subType = info.getSubtypeName();
        isConnected = info.isConnected();
        isFailover = info.isFailover();
        isRoaming = info.isRoaming();
        ip = NetworkUtil.getIpAddressString();
    }

    public JSONObject toJsonObject() throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type);
        jsonObject.put("subType", subType);
        jsonObject.put("isConnected", isConnected);
        jsonObject.put("isFailover", isFailover);
        jsonObject.put("isRoaming", isRoaming);
        jsonObject.put("ip", ip);
        return jsonObject;
    }
}
