package com.duchen.template.usage;

import android.app.Application;
import android.content.res.Configuration;

import com.duchen.template.usage.Util.LogUtil;

/**
 * Created by netease on 17/2/12.
 */
public class MainApplication extends Application {

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogUtil.LogD("onTrimMemory");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LogUtil.LogD("onLowMemory");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtil.LogD("onConfigurationChanged");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.LogD("onCreate");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        LogUtil.LogD("onTerminate");
    }
}
