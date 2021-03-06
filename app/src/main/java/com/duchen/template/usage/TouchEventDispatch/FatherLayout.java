package com.duchen.template.usage.TouchEventDispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.duchen.template.utils.DLog;


public class FatherLayout extends FrameLayout {

    private static final int THRESHOLD = 3;

    private int count = 0;

    public FatherLayout(Context context) {
        super(context);
    }

    public FatherLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FatherLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        DLog.d("--------------FATHER dispatchTouchEvent : " + DLog.eventToString(ev.getAction()));
        boolean returnVale = super.dispatchTouchEvent(ev);
        DLog.d("--------------FATHER dispatchTouchEvent return : " + returnVale);
        DLog.d("--");
        return returnVale;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean returnVale;
        returnVale = false;
        if (count < THRESHOLD) {
            count++;
        } else {
            returnVale = true;
        }
        DLog.d("FATHER onInterceptTouchEvent return : " + returnVale);
        return returnVale;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean returnVale = false;

        DLog.d("FATHER onTouchEvent return : " + returnVale);
        return returnVale;
    }

    public void reset() {
        count = 0;
    }
}
