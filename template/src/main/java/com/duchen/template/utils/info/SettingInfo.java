package com.duchen.template.utils.info;

import android.app.Service;
import android.media.AudioManager;

import com.duchen.template.component.ApplicationBase;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingInfo {

    private int volumeValue;
    private long time;
    private boolean isAutoUpdate;
    private boolean isLoopCheck;
    private String foregroundApp;

    public SettingInfo() {
        AudioManager audioManager = (AudioManager) ApplicationBase.getInstance().getSystemService(Service.AUDIO_SERVICE);
        int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int cur = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        this.volumeValue = (int) (cur / (max * 1.0f) * 100);
        this.time = System.currentTimeMillis();
    }

    public JSONObject toJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("volumeValue", volumeValue);
        jsonObject.put("time", time);
        return jsonObject;
    }
}
