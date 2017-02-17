package com.duchen.template.usage.TouchEventDispatch;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.duchen.template.usage.ActivityBase;
import com.duchen.template.usage.Util.LogUtil;
import com.duchen.template.usage.R;

public class TestDispatchActivity extends ActivityBase {

    private FatherLayout mFatherLayout;
    private ChildView mChildView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dispatch);
        findViewById(R.id.reset).setOnClickListener(this);
        mFatherLayout = (FatherLayout) findViewById(R.id.father);
        mChildView = (ChildView) findViewById(R.id.child);
    }

    @Override
    public void handleClick(int id, View v) {
        switch (id) {
            case R.id.reset:
                mFatherLayout.reset();
                mChildView.reset();
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.LogActivityD("-------------------ACTIVITY dispatchTouchEvent : " + LogUtil.eventToString(ev.getAction()));
        boolean returnVale = super.dispatchTouchEvent(ev);
        LogUtil.LogActivityD("-------------------ACTIVITY dispatchTouchEvent return : " + returnVale);
        LogUtil.LogActivityD("--");
        LogUtil.LogActivityD("--");
        return returnVale;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean returnVale = true;
        LogUtil.LogActivityD("ACTIVITY onTouchEvent : " + LogUtil.eventToString(event.getAction()) + "  return : " + returnVale);
        return returnVale;
    }
}
