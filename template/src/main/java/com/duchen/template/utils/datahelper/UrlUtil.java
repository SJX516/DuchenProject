package com.duchen.template.utils.datahelper;

import android.net.Uri;
import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.Set;

/**
 * Url处理工具类
 * Created by hzniqun on 16/4/7.
 */
public class UrlUtil {
    static final String URLCharTable = "!#$%&'()*+,-./:;=?@[\\]^_`{|}~";

    /**
     * 提取指定字符串中的链接地址
     */
    public static String getHttpLink(String str, int offset) {
        if (TextUtils.isEmpty(str)) return "";

        int len;
        if (StringUtil.startsWithIgnoreCase(str, offset, "http://")) {
            len = "http://".length();
        } else if (StringUtil.startsWithIgnoreCase(str, offset, "www.")) {
            len = "www.".length();
        } else if (StringUtil.startsWithIgnoreCase(str, offset, "wap.")) {
            len = "wap.".length();
        } else if (StringUtil.startsWithIgnoreCase(str, offset, "https://")) {
            len = "https://".length();
        } else {
            return null;
        }

        int strLen = str.length();

        while (offset + len < strLen) {
            char c = str.charAt(offset + len);
            if ((c >= 'A' && c <= 'Z') // 'a' - 'z'
                    || (c >= 'a' && c <= 'z') // 'A' - 'Z'
                    || (c >= '0' && c <= '9')) { // '0' - '9'
                len++;
            } else {
                if (URLCharTable.indexOf(c) >= 0) {
                    len++;
                } else {
                    break;
                }
            }
        }

        return str.substring(offset, offset + len);
    }

    /**
     * 格式化float保留两位小数
     */
    public static String formatFloat2Decimal(float value) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(value);
    }

    /**
     * 提取Uri参数
     *
     * @param uriString uri字符串
     * @param paramKey  参数key
     * @return 参数值
     */
    public static String extractUriQueryParamValue(final String uriString, final String paramKey) {
        if (TextUtils.isEmpty(uriString)) return "";
        if (TextUtils.isEmpty(paramKey)) return "";

        Uri uri = Uri.parse(uriString);
        Set<String> queryKeySet = uri.getQueryParameterNames();

        if (queryKeySet != null && !queryKeySet.isEmpty()) {
            for (String key : queryKeySet) {
                if (key.equalsIgnoreCase(paramKey)) {
                    return uri.getQueryParameter(key);
                }
            }
        }
        return "";
    }

    /**
     * 替换Url的参数
     *
     * @param originUrl  url原字符串
     * @param paramKey   参数的key
     * @param paramValue 替换的value
     * @return 替换后的url
     */
    public static String replaceOrAppendUriQueryParamValue(final String originUrl, final String paramKey, final
    String paramValue) {
        if (TextUtils.isEmpty(originUrl)) return originUrl;
        if (TextUtils.isEmpty(paramKey)) return originUrl;
        Uri uri = Uri.parse(originUrl);

        final Set<String> params = uri.getQueryParameterNames();
        Uri.Builder newUri;
        if (params.contains(paramKey)) {
            newUri = uri.buildUpon().clearQuery();
            for (String param : params) {
                String value;
                if (param.equals(paramKey)) {
                    value = paramValue;
                } else {
                    value = uri.getQueryParameter(param);
                }
                newUri.appendQueryParameter(param, value);
            }
        } else {
            newUri = uri.buildUpon();
            newUri.appendQueryParameter(paramKey, paramValue);
        }

        return newUri.toString();
    }

    /**
     * 给指定url地址添加http://
     */
    public static String addHttpProtocol(final String url) {
        if (TextUtils.isEmpty(url)) return "";
        if (!url.contains("://")) {
            return "http://" + url;
        }
        return url;
    }

    /**
     * 移除url地址中拼接的参数
     *
     * @param url   原url
     * @param param 参数
     * @return 移除后的url
     */
    public static String removeUriQuery(final String url, final String param) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(param)) return url;
        // ?param&  ---> ?
        String query = "?" + param + "&";
        if (url.contains(query)) return url.replace(param + "&", "");

        // ?param   --->  ""
        query = "?" + param;
        if (url.contains(query)) return url.replace("?" + param, "");

        // &param  ----> ""
        query = "&" + param;
        if (url.contains(query)) return url.replace("&" + param, "");

        return url;
    }

    /**
     * 在原有url的前面插入指定的host
     *
     * @param originalUri http://video.study.163.com
     * @param CDNHost     10.240.33.34
     * @return http://10.240.33.34/video.study.163.com
     */
    public static String addHostToUrl(String originalUri, String CDNHost) {
        if (TextUtils.isEmpty(originalUri) || TextUtils.isEmpty(CDNHost)) {
            return originalUri;
        }
        Uri uri = Uri.parse(originalUri);
        String schema = uri.getScheme();
        String path = uri.getPath();
        String query = uri.getQuery();
        String host = uri.getHost();
        String newUrl = schema + "://" + CDNHost + "/" + host + path;
        if (!TextUtils.isEmpty(query)) {
            newUrl = newUrl + "?" + query;
        }
        return newUrl;
    }
}
