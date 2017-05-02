package com.duchen.template.module;

import android.util.Pair;

import com.duchen.template.concept.IModuleConfig;

public interface TemplateConfig extends IModuleConfig {

    Pair<String, String> getHostAndRequestPath();

    boolean isDebug();

    boolean isUseHttps();

}
