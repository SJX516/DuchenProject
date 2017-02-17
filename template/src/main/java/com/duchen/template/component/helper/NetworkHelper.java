package com.duchen.template.component.helper;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.duchen.template.component.BaseApplication;


public class NetworkHelper {

    private static final String TAG = "NetworkHelper";
    public static final int NETWORK_CLASS_UNKNOWN = 0;
    public static final int NETWORK_CLASS_2_G = 1;
    public static final int NETWORK_CLASS_3_G = 2;
    public static final int NETWORK_CLASS_4_G = 3;
    public static final int NETWORK_CLASS_WIFI = 10;
    private ConnectivityManager cm;
    private static NetworkHelper instance;

    private NetworkHelper() {
    }

    public synchronized static NetworkHelper getInstance() {
        if (instance == null) {
            instance = new NetworkHelper();
        }
        return instance;
    }

    // 获取当前的网络信息：2g/3g/4g/wifi
    public NetworkInfo getCurNetworkInfo() {
        if (cm == null) {
            cm = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        return cm.getActiveNetworkInfo();
    }

    /**
     * 是否处于wifi网络环境下
     *
     * @return
     */
    public boolean isWifiNetwork() {
        NetworkInfo info = getCurNetworkInfo();
        return (info != null && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * 是否处于数据网络下
     *
     * @return
     */
    public boolean isMobileNetwork() {
        NetworkInfo info = getCurNetworkInfo();
        return (info != null && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    public boolean is2G() {
        return getNetworkType() == NETWORK_CLASS_2_G;
    }

    public boolean is3G() {
        return getNetworkType() == NETWORK_CLASS_3_G;
    }

    public boolean is4G() {
        return getNetworkType() == NETWORK_CLASS_4_G;
    }

    /**
     * 是否有网络连接
     *
     * @return
     */
    public boolean hasNetworkConnection() {
        NetworkInfo info = getCurNetworkInfo();
        return (info != null && info.isConnectedOrConnecting());
    }

    /**
     * 网络状况变化监听器
     *
     * @author hzsongyuming
     */
    public static interface NetworkChangeListener {
        public void onNetworkChange(Intent intent, NetworkInfo info);
    }

    private int getNetworkType() {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    switch (networkInfo.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            return NETWORK_CLASS_2_G;
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            return NETWORK_CLASS_3_G;
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            return NETWORK_CLASS_4_G;
                        default:
                            return NETWORK_CLASS_UNKNOWN;
                    }
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return NETWORK_CLASS_WIFI;
                }
            }
        }
        return NETWORK_CLASS_UNKNOWN;
    }
}
