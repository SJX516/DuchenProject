package com.duchen.template.usage.TestAutoLoopViewPager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.duchen.template.ui.AbsIndicator;
import com.duchen.template.usage.R;

public class TestIndicator extends AbsIndicator {
    private Drawable mDefaultBmp;
    private Drawable mHighlightBmp;
    private int mCount;

    public TestIndicator(Context context) {
        this(context, null);
    }

    public TestIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDefaultBmp = context.getResources().getDrawable(R.drawable.shape_gray_rect);
        mHighlightBmp = context.getResources().getDrawable(R.drawable.shape_white_rect);
        mDefaultBmp.setBounds(0, 0, mDefaultBmp.getIntrinsicWidth(), mDefaultBmp.getIntrinsicHeight());
        mHighlightBmp.setBounds(0, 0, mHighlightBmp.getIntrinsicWidth(),
                mHighlightBmp.getIntrinsicHeight());
        setGap(6);
    }

    public void setCount(int count) {
        mCount = count;
        requestLayout();
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Drawable getIndicator() {
        return mDefaultBmp;
    }

    @Override
    public Drawable getHighlight() {
        return mHighlightBmp;
    }

    public void clear() {
        mDefaultBmp = null;
        mHighlightBmp = null;
    }
}
