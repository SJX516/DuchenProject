package com.duchen.template.usage.TouchEventDispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.duchen.template.usage.Util.LogUtil;

/**
 * Created by netease on 17/1/17.
 */
public class ChildView extends TextView {

    private static final int THRESHOLD = 2;

    private int count = 0;

    public ChildView(Context context) {
        super(context);
    }

    public ChildView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean returnVale = super.dispatchTouchEvent(ev);
        LogUtil.LogD("CHILD dispatchTouchEvent return : " + returnVale);
        return returnVale;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean returnVale;
        returnVale = true;
        LogUtil.LogD("CHILD onTouchEvent return : " + returnVale);
        return returnVale;
    }

    public void reset() {
        count = 0;
    }

}
