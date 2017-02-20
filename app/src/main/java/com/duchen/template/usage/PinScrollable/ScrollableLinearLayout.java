package com.duchen.template.usage.PinScrollable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.duchen.template.ui.HeaderViewDragger;

public class ScrollableLinearLayout extends LinearLayout {

    private HeaderViewDragger mDragger;

    public ScrollableLinearLayout(Context context) {
        super(context);
    }

    public ScrollableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initConfig(HeaderViewDragger.Callback callback, int headerHeight) {
        mDragger = new HeaderViewDragger(callback, headerHeight, true);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(mDragger == null) {
            return super.dispatchTouchEvent(ev);
        }
        boolean handled = mDragger.handleTouchEvent(ev);
        return handled || super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
