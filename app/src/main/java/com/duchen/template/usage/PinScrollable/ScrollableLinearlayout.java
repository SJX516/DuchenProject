package com.duchen.template.usage.PinScrollable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ScrollableLinearLayout extends LinearLayout {

    interface Callback {
        boolean tryScroll(boolean isVerticalMove, float scrollValue);
    }

    private Callback mCallback;
    private boolean mIsDispatchLeftEventEnable = false;

    private boolean mTouchStart = false;
    private boolean mIsHandleThisEventList = false;
    private boolean mIsSkipTryScroll = false;
    private int mStartY, mMoveY;
    private int mStartX, mMoveX;

    public ScrollableLinearLayout(Context context) {
        super(context);
    }

    public ScrollableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void setDispatchLeftEventEnable(boolean dispatchLeftEventEnable) {
        mIsDispatchLeftEventEnable = dispatchLeftEventEnable;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean handled = false;
        if (mCallback == null) {
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mTouchStart) {
                    mMoveX = mStartX = (int) ev.getX();
                    mMoveY = mStartY = (int) ev.getY();
                    mTouchStart = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mTouchStart) {
                    mMoveX = mStartX = (int) ev.getX();
                    mMoveY = mStartY = (int) ev.getY();
                    mTouchStart = true;
                } else {
                    if (mIsSkipTryScroll) {
                        handled = false;
                    } else {
                        //该View是否需要处理该滑动
                        handled = mCallback.tryScroll(isVerticalMove(), getDeltaForCurrentDirection(ev));
                    }
                    //如果之前处理了这次不再处理,模拟一次down事件
                    if (mIsHandleThisEventList && !handled && mIsDispatchLeftEventEnable) {
                        MotionEvent motionEvent = MotionEvent.obtain(ev.getDownTime(),
                                ev.getEventTime(), MotionEvent.ACTION_DOWN,
                                ev.getX(), ev.getY(), ev.getMetaState());
                        mIsSkipTryScroll = true;
                        mIsHandleThisEventList = false;
                        return super.dispatchTouchEvent(motionEvent);
                    }
                    mMoveX = (int) ev.getX();
                    mMoveY = (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
                mTouchStart = false;
                mIsSkipTryScroll = false;
                mIsHandleThisEventList = false;
                handled = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                mTouchStart = false;
                mIsHandleThisEventList = false;
                mIsSkipTryScroll = false;
                handled = false;
            default:
                break;
        }

        if (handled) {
            cancelEvent(ev, this);
            mIsHandleThisEventList = true;
        }
        return handled || super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    private void cancelEvent(MotionEvent ev, ViewGroup viewGroup) {
        View tempView;
        for(int i = 0 ; i < viewGroup.getChildCount(); i++) {
            tempView = viewGroup.getChildAt(i);
            if(tempView instanceof ViewGroup) {
                MotionEvent motionEvent = MotionEvent.obtain(ev.getDownTime(),
                        ev.getEventTime(), MotionEvent.ACTION_CANCEL,
                        ev.getX(), ev.getY(), ev.getMetaState());

                tempView.dispatchTouchEvent(motionEvent);
                motionEvent.recycle();
            }
        }
    }

    private boolean isVerticalMove() {
        return Math.abs(mStartY - mMoveY) > 2 * Math.abs(mStartX - mMoveX);
    }

    private int getDeltaForCurrentDirection(MotionEvent event) {
        if (isVerticalMove()) {
            return (int) (mMoveY - event.getY());
        } else {
            return (int) (mMoveX - event.getX());
        }

    }

}
