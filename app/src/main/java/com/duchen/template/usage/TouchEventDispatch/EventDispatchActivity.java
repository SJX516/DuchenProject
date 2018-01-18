package com.duchen.template.usage.TouchEventDispatch;

import android.view.MotionEvent;
import android.view.View;

import com.duchen.template.usage.AppActivityBase;
import com.duchen.template.usage.R;
import com.duchen.template.utils.DLog;

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
        findViewById(R.id.btn_reset).setOnClickListener(this);
        mFatherLayout = (FatherLayout) findViewById(R.id.father_view);
        mChildView = (ChildView) findViewById(R.id.child_view);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void handleClick(int id, View v) {
        switch (id) {
            case R.id.btn_reset:
                mFatherLayout.reset();
                mChildView.reset();
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        DLog.d("-------------------ACTIVITY dispatchTouchEvent : " + DLog.eventToString(ev.getAction()));
        boolean returnVale = super.dispatchTouchEvent(ev);
        DLog.d("-------------------ACTIVITY dispatchTouchEvent return : " + returnVale);
        DLog.d("--");
        DLog.d("--");
        return returnVale;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean returnVale = true;
        DLog.d("ACTIVITY onTouchEvent : " + DLog.eventToString(event.getAction()) + "  return : " + returnVale);
        return returnVale;
    }
}
