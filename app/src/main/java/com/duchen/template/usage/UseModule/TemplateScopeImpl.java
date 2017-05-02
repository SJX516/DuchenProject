package com.duchen.template.usage.UseModule;

import android.content.Context;

import com.duchen.template.module.TemplateConfig;
import com.duchen.template.module.TemplateScope;
import com.duchen.template.usage.MainActivity;
import com.duchen.template.usage.MainApplication;


public class TemplateScopeImpl implements TemplateScope {

    private TemplateConfig mConfig;

    public TemplateScopeImpl(TemplateConfig config) {
        mConfig = config;
    }

    @Override
    public TemplateConfig getConfig() {
        return mConfig;
    }

    @Override
    public boolean isMainActivityDestroyed() {
        return MainApplication.getInstance().isMainActivityDestroyed();
    }

    @Override
    public void launchNewMainActivity(Context context) {
        MainActivity.launchNewTask(context);
    }
}
