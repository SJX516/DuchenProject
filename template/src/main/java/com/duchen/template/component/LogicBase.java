package com.duchen.template.component;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.duchen.template.concept.ILogic;
import com.duchen.template.utils.LogUtil;

import java.lang.ref.WeakReference;

/**
 * 逻辑控制基类
 */
public abstract class LogicBase implements ILogic {

    private final static String TAG = "LogicBase";

    // Handler的弱引用
    protected WeakReference<Handler> mHandlerHost;
    protected WeakReference<Context> mContextHost;
    private boolean mIsReleased = true;

    public LogicBase(Context context, Handler handler) {
        mIsReleased = false;
        mContextHost = new WeakReference<>(context);
        mHandlerHost = new WeakReference<>(handler);
    }

    protected void notifyUi(int what) {
        Handler handler = mHandlerHost.get();
        if (!mIsReleased && handler != null) {
            handler.sendEmptyMessage(what);
        }
    }

    protected void notifyUi(Message msg) {
        if (mIsReleased) {
            return;
        }
        Handler handler = mHandlerHost.get();
        if (handler != null) {
            handler.sendMessage(msg);
        }
    }

    protected void notifyUiDelay(int what, long delayMillis) {
        if (mIsReleased) {
            return;
        }
        Handler handler = mHandlerHost.get();
        if (handler != null) {
            handler.sendEmptyMessageDelayed(what, delayMillis);
        }
    }

    protected boolean isReleased() {
        return mIsReleased;
    }

    protected void removeMessages() {
    }

    @Override
    public void release() {
        mIsReleased = true;
        if (mHandlerHost.get() == null) {
            return;
        }
        removeMessages();
        LogUtil.d(TAG, "release");
        mHandlerHost.clear();
    }
}
