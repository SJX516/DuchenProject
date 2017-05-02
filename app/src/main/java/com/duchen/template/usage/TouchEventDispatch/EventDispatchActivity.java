package com.duchen.template.usage.TouchEventDispatch;

import android.view.MotionEvent;
import android.view.View;

import com.duchen.template.usage.AppActivityBase;
import com.duchen.template.usage.R;
import com.duchen.template.utils.LogUtil;

/**
 * 验证touchEvent从activity到最底层view的分发机制,成果在笔记《View Touch事件分发机制》中
 */
public class EventDispatchActivity extends AppActivityBase {

    private FatherLayout mFatherLayout;
    private ChildView mChildView;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_test_dispatch);
    }

    @Override
    public void findViews() {
        findViewById(R.id.reset).setOnClickListener(this);
        mFatherLayout = (FatherLayout) findViewById(R.id.father);
        mChildView = (ChildView) findViewById(R.id.child);
    }

    @Override
    public void initViews() {

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
