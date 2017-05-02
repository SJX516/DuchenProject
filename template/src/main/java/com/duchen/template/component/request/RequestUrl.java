package com.duchen.template.component.request;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;

import com.duchen.template.module.TemplateInstance;

public class RequestUrl {

    private static final String PROTOCOL_HTTP = "http";
    private static final String PROTOCOL_HTTPS = "https";


    public static String getUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        Uri uri = Uri.parse(url);
        if (uri != null && !TextUtils.isEmpty(uri.getHost())) {
            return url;
        }

        Pair<String, String> hostAndPath = TemplateInstance.getInstance().getScope().getConfig().getHostAndRequestPath();
        String host = hostAndPath.first;
        host += "/" + hostAndPath.second;
        return getUrl(url, host);
    }

    public static String getUrl(String url, String host) {
        String returnUrl = (TemplateInstance.getInstance().getScope().getConfig().isUseHttps() ? PROTOCOL_HTTPS : PROTOCOL_HTTP);
        returnUrl += "://";
        returnUrl += host;
        returnUrl += url;
        return returnUrl;
    }
}
