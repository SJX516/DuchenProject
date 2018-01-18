package com.duchen.template.usage;

import android.content.res.Configuration;

import com.duchen.template.component.ApplicationBase;
import com.duchen.template.module.impl.TemplateModuleImpl;
import com.duchen.template.usage.UseModule.ModuleFactory;
import com.duchen.template.module.TemplateScope;
import com.duchen.template.usage.UseModule.TemplateConfigImpl;
import com.duchen.template.usage.UseModule.TemplateScopeImpl;
import com.duchen.template.utils.DLog;


public class MainApplication extends ApplicationBase {

    private TemplateScope mTemplateScope;
    private boolean mIsMainActivityDestroyed = true;

    public static MainApplication getInstance() {
        return ApplicationBase.getInstance();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        DLog.d("onTrimMemory");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        DLog.d("onLowMemory");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        DLog.d("onConfigurationChanged");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DLog.d("onCreate");
        initModuleConfigs();
        initModuleFactory();
    }


    private void initModuleConfigs() {
        mTemplateScope = new TemplateScopeImpl(new TemplateConfigImpl());
    }

    private void initModuleFactory() {
        ModuleFactory.getInstance().registerModule(ModuleFactory.ModuleType.TEMPLATE, new TemplateModuleImpl(mTemplateScope));
    }

    public void templateDoSomeThing() {
        ModuleFactory.getInstance().getTemplateModule().doSomeThing();
    }

    @Override
    protected void initNetworkStatusReceiver() {

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DLog.d("onTerminate");
    }

    public boolean isMainActivityDestroyed() {
        return mIsMainActivityDestroyed;
    }

    public void setMainActivityDestroyed(boolean isMainActivityDestroyed) {
        this.mIsMainActivityDestroyed = isMainActivityDestroyed;
    }
}
