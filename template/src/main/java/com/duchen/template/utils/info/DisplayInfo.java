package com.duchen.template.utils.info;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import com.duchen.template.component.ApplicationBase;

import org.json.JSONException;
import org.json.JSONObject;

public class DisplayInfo {

    private int id;
    private int heightPx;
    private int widthPx;
    private float xdpi;
    private float ydpi;
    private float fps;
    private float density;
    private int rotation;
    private boolean secure;

    public DisplayInfo() {
        WindowManager wm = (WindowManager) ApplicationBase.getInstance().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        DisplayMetrics real = new DisplayMetrics();
        display.getRealMetrics(real);

        rotation = display.getRotation();

        id = display.getDisplayId();
        heightPx = real.heightPixels;
        widthPx = real.widthPixels;
        fps = display.getRefreshRate();
        density = real.density;
        rotation = rotationToDegrees(rotation);
        secure = (display.getFlags() & Display.FLAG_SECURE) == Display.FLAG_SECURE;
        if(rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
            xdpi = real.ydpi;
            ydpi = real.xdpi;
        } else {
            xdpi = real.xdpi;
            ydpi = real.ydpi;
        }
    }

    public JSONObject toJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("heightPx", heightPx);
        jsonObject.put("widthPx", widthPx);
        jsonObject.put("id", id);
        jsonObject.put("fps", fps);
        jsonObject.put("secure", secure);
        jsonObject.put("rotation", rotation);
        jsonObject.put("xdpi", xdpi);
        jsonObject.put("ydpi", ydpi);
        jsonObject.put("density", getDpiDesc(density));
        return jsonObject;
    }

    private String getDpiDesc(float density) {
        if (density <= 0.75f) {
            return "LDPI";
        } else if (density <= 1.0f) {
            return "MDPI";
        } else if (density <= 1.5f) {
            return "HDPI";
        } else if (density <= 2.0f) {
            return "XHDPI";
        } else {
            return "XXHDPI";
        }

    }

    private int rotationToDegrees(int rotation) {
        switch (rotation) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
            default:
                return -1;
        }
    }
}
