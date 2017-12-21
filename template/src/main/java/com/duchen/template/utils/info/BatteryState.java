package com.duchen.template.utils.info;

import android.content.Intent;
import android.os.BatteryManager;

import org.json.JSONException;
import org.json.JSONObject;

public class BatteryState {
    private int health;
    private int level;
    private int source;
    private int scale;
    private int status;
    private String tech;
    private int temp;
    private int voltage;

    public BatteryState(Intent intent) {
        health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_UNKNOWN);
        level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        source = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
        scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
        status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
        tech = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
        temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
        voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
    }

    public JSONObject toJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", statusLabel(status));
        jsonObject.put("health", healthLabel(health));
        jsonObject.put("source", sourceLabel(source));
        jsonObject.put("level", level / (scale * 1.0f) * 100);
        jsonObject.put("temp", temp / 10.0);
        jsonObject.put("voltage", voltage / 1000.0);
        return jsonObject;
    }

    private String healthLabel(int health) {
        switch (health) {
            case BatteryManager.BATTERY_HEALTH_COLD:
                return "过冷";
            case BatteryManager.BATTERY_HEALTH_GOOD:
                return "良好";
            case BatteryManager.BATTERY_HEALTH_DEAD:
                return "没电";
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                return "过电压";
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                return "过热";
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                return "未知";
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                return "未知";
            default:
                return "未知_" + health;
        }
    }

    private String sourceLabel(int source) {
        switch (source) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                return "AC";
            case BatteryManager.BATTERY_PLUGGED_USB:
                return "USB";
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                return "WIRELESS";
            default:
                return "unknown_" + source;
        }
    }

    private String statusLabel(int status) {
        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                return "充电中";
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                return "放电中";
            case BatteryManager.BATTERY_STATUS_FULL:
                return "已充满";
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                return "未充电";
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                return "未知";
            default:
                return "未知_" + status;
        }
    }
}