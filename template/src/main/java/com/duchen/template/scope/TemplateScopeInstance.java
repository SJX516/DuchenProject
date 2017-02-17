package com.duchen.template.scope;

import android.content.Context;
import android.util.Pair;


public class TemplateScopeInstance implements ITemplateScope {

    private static TemplateScopeInstance sInstance;
    private ITemplateScope mRealScope;

    private TemplateScopeInstance() {

    }

    public static synchronized TemplateScopeInstance getInstance() {
        if (sInstance == null) {
            sInstance = new TemplateScopeInstance();
        }
        return sInstance;
    }

    public void init(ITemplateScope configurations) {
        mRealScope = configurations;
    }

    @Override
    public Pair<String, String> getHostAndRequestPath() {
        return mRealScope.getHostAndRequestPath();
    }

    @Override
    public boolean isDebug() {
        return mRealScope.isDebug();
    }

    @Override
    public boolean isUseHttps() {
        return mRealScope.isUseHttps();
    }

    @Override
    public boolean isMainActivityDestroyed() {
        return mRealScope.isMainActivityDestroyed();
    }

    @Override
    public void launchNewMainActivity(Context context) {
        mRealScope.launchNewMainActivity(context);
    }
}
