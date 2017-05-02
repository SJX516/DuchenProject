package com.duchen.template.usage;

import android.content.res.Configuration;

import com.duchen.template.component.ApplicationBase;
import com.duchen.template.utils.LogUtil;


public class MainApplication extends ApplicationBase {

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogUtil.d("onTrimMemory");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LogUtil.d("onLowMemory");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtil.d("onConfigurationChanged");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("onCreate");
    }

    @Override
    protected void initNetworkStatusReceiver() {

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        LogUtil.d("onTerminate");
    }
}
