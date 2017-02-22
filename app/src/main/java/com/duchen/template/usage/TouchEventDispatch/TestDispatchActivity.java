package com.duchen.template.usage.TouchEventDispatch;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.duchen.template.usage.AppActivityBase;
import com.duchen.template.usage.R;
import com.duchen.template.utils.LogUtil;

public class TestDispatchActivity extends AppActivityBase {

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
        LogUtil.d("-------------------ACTIVITY dispatchTouchEvent : " + LogUtil.eventToString(ev.getAction()));
        boolean returnVale = super.dispatchTouchEvent(ev);
        LogUtil.d("-------------------ACTIVITY dispatchTouchEvent return : " + returnVale);
        LogUtil.d("--");
        LogUtil.d("--");
        return returnVale;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean returnVale = true;
        LogUtil.d("ACTIVITY onTouchEvent : " + LogUtil.eventToString(event.getAction()) + "  return : " + returnVale);
        return returnVale;
    }
}
