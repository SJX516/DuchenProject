package com.duchen.template.component.helper;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

import com.duchen.template.utils.DLog;


public class ActivityLifecycle implements ActivityLifecycleCallbacks {

    private final static String TAG = "ActivityLifecycle";

    private Activity mCurrentActivity;

    private boolean isActivityVisible;

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public boolean hasVisibleActivity() {
        return isActivityVisible;
    }

    public void reset() {
        mCurrentActivity = null;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        DLog.d(TAG, "onActivityCreated : " + activity.getLocalClassName());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        DLog.d(TAG, "onActivityStarted : " + activity.getLocalClassName());
    }

    @Override
    public void onActivityResumed(Activity activity) {
        DLog.d(TAG, "onActivityResumed : " + activity.getLocalClassName());
        isActivityVisible = true;
        mCurrentActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        DLog.d(TAG, "onActivityPaused : " + activity.getLocalClassName());
        isActivityVisible = false;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        DLog.d(TAG, "onActivityStopped : " + activity.getLocalClassName());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        DLog.d(TAG, "onActivitySaveInstanceState : " + activity.getLocalClassName());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        DLog.d(TAG, "onActivityDestroyed : " + activity.getLocalClassName());
    }
}
