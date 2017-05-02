package com.duchen.template.usage.UseModule;

import android.util.Pair;

import com.duchen.template.module.TemplateConfig;

public class TemplateConfigImpl implements TemplateConfig {

    public TemplateConfigImpl() {
    }

    @Override
    public Pair<String, String> getHostAndRequestPath() {
        return new Pair<>("test.duchen.com", "usage");
    }

    @Override
    public boolean isDebug() {
        return true;
    }

    @Override
    public boolean isUseHttps() {
        return false;
    }
}
