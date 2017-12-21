package com.duchen.template.utils.info;


import android.os.SystemClock;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class CpuInfo {

    private String name;
    private String maxFreq;
    private String minFreq;
    private String currentFreq;
    private long bootLastTime;

    public CpuInfo() {
        name = getCpuName();
        maxFreq = getMaxCpuFreq();
        minFreq = getMinCpuFreq();
        currentFreq = getCurCpuFreq();
        bootLastTime = SystemClock.elapsedRealtime();
    }

    public JSONObject toJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("maxFreq", maxFreq);
        jsonObject.put("minFreq", minFreq);
        jsonObject.put("currentFreq", currentFreq);
        jsonObject.put("bootLastTime", bootLastTime);
        return jsonObject;
    }

    public static String getMaxCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            result = result + "Hz";
            in.close();
        } catch (IOException ex) {
            result = "N/A";
        }
        return result.trim();
    }

    // 获取CPU最小频率（单位KHZ）
    public static String getMinCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            result = result + "Hz";
            in.close();
        } catch (IOException ex) {
            result = "N/A";
        }
        return result.trim();
    }

    // 实时获取CPU当前频率（单位KHZ）
    public static String getCurCpuFreq() {
        String result = "";
        try {
            FileReader fr = new FileReader(
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim() + "Hz";
        } catch (Exception e) {
            result = "N/A";
        }
        return result;
    }

    public static String getCpuName() {
        String result = "";
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            if (array.length >= 2) {
                return array[1];
            }
        } catch (Exception e) {
            result = "N/A";
        }
        return result;
    }
}
