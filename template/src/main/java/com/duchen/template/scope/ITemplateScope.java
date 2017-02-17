package com.duchen.template.scope;

import android.content.Context;
import android.util.Pair;

public interface ITemplateScope {

    Pair<String, String> getHostAndRequestPath();

    boolean isDebug();

    boolean isUseHttps();

    boolean isMainActivityDestroyed();

    void launchNewMainActivity(Context context);
}
