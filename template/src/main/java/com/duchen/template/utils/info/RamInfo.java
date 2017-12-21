package com.duchen.template.utils.info;

import android.app.ActivityManager;
import android.content.Context;
import android.text.format.Formatter;

import com.duchen.template.component.ApplicationBase;
import com.duchen.template.utils.storage.StorageUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class RamInfo {

    private long availMem;
    private long totalMem;
    private boolean lowMemory;
    private long availRomSize;

    public RamInfo() {
        ActivityManager activityManager = (ActivityManager) ApplicationBase.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        availMem = memoryInfo.availMem;
        totalMem = memoryInfo.totalMem;
        lowMemory = memoryInfo.lowMemory;
        availRomSize = StorageUtil.getAvailableExternalMemorySize();
    }

    public JSONObject toJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("availMem", Formatter.formatFileSize(ApplicationBase.getInstance(), availMem));
        jsonObject.put("totalMem", Formatter.formatFileSize(ApplicationBase.getInstance(), totalMem));
        jsonObject.put("lowMemory", lowMemory);
        jsonObject.put("availRomSize", Formatter.formatFileSize(ApplicationBase.getInstance(), availRomSize));
        return jsonObject;
    }
}
