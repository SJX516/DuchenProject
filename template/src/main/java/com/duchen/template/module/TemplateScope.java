package com.duchen.template.module;

import android.content.Context;

import com.duchen.template.concept.IScope;

public interface TemplateScope extends IScope {

    TemplateConfig getConfig();

    boolean isMainActivityDestroyed();

    void launchNewMainActivity(Context context);
}
