package com.duchen.template.component;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.duchen.template.component.helper.ActivityLifecycle;
import com.duchen.template.component.helper.NetworkHelper.NetworkChangeListener;
import com.duchen.template.concept.ExceptionBase;
import com.duchen.template.utils.LogUtil;
import com.duchen.template.utils.PlatformUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class ApplicationBase extends Application {

    private static final String TAG = "BaseApplication";

    protected static ApplicationBase sBaseApp;
    private static ExecutorService sThreadPool = null;

    private ActivityLifecycle mActivityLifecycle = new ActivityLifecycle();

    public static <T extends ApplicationBase> T getInstance() {
        if (sBaseApp == null) {
            LogUtil.e(TAG, "sBaseApp not create or be terminated!");
        }
        return (T) sBaseApp;
    }

    private static ExecutorService getThreadPool() {
        if (null == sThreadPool) {
            sThreadPool = Executors.newCachedThreadPool();
        }
        return sThreadPool;
    }

    public Activity getCurrentActivity() {
        if (mActivityLifecycle != null) {
            return mActivityLifecycle.getCurrentActivity();
        }
        return null;
    }

    public void resetActivityLifecycle() {
        if (mActivityLifecycle != null) {
            mActivityLifecycle.reset();
        }
    }

    public boolean hasVisibleActivity() {
        if (mActivityLifecycle != null) {
            return mActivityLifecycle.hasVisibleActivity();
        }
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sBaseApp = this;
        mNetworkListeners = new ArrayList<WeakReference<NetworkChangeListener>>();
        registerActivityLifecycleCallbacks(mActivityLifecycle);
        if (PlatformUtil.inMainProcess()) {
            try {
                listenToNetworkStatusChange();
            } catch (ExceptionBase e) {
                LogUtil.e(TAG, e.getMessage());
            }
        }
    }

    // 网络相关的
    protected BroadcastReceiver mNetworkStatusReceiver;
    protected List<WeakReference<NetworkChangeListener>> mNetworkListeners;

    private void listenToNetworkStatusChange() throws ExceptionBase {
        initNetworkStatusReceiver();
        if (mNetworkStatusReceiver == null) {
            throw new ExceptionBase("mNetworkStatusReceiver is null!");
        }

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkStatusReceiver, filter);
    }

    protected abstract void initNetworkStatusReceiver();

    /**
     * 添加网络变化监听器
     */
    public void addNetworkChangeListener(NetworkChangeListener l) {
        for (WeakReference<NetworkChangeListener> listener : mNetworkListeners) {
            if (listener.get() != null && listener.get() == l) {
                return;
            }
        }

        mNetworkListeners.add(new WeakReference<NetworkChangeListener>(l));
    }

    /**
     * 移除指定的网络变化监听器
     */
    public void removeNetworkChangeListener(NetworkChangeListener l) {
        WeakReference<NetworkChangeListener> found = null;
        for (WeakReference<NetworkChangeListener> listener : mNetworkListeners) {
            if (found == null && listener.get() != null && listener.get() == l) {
                found = listener;
            }
        }
        if (found != null) {
            mNetworkListeners.remove(found);
        }
    }
}
