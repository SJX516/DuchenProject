package com.duchen.template.usage.TestLifeCircle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.duchen.template.usage.AppActivityBase;
import com.duchen.template.usage.R;
import com.duchen.template.utils.LogUtil;

import java.util.ArrayList;

/**
 * 试验发现launchMode为standard,然后反复启动这个activity的话,GC并不会去主动调用onDestroy方法,
 * 而是在调用了onDestroy之后,这个activity才有可能被GC回收!!!
 */
public class LifeCircleActivityA extends AppActivityBase {

    private ArrayList<Long> mLongs;
    private Object nullObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(this.toString() + "  onCreate()");
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_test_lifecircle_a);
    }

    @Override
    public void findViews() {
        findViewById(R.id.btn_jump).setOnClickListener(this);
    }

    @Override
    public void initViews() {
        mLongs = new ArrayList<>(3000000);
        mLongs = null;
    }

    @Override
    public void handleClick(int id, View v) {
        nullObject.toString();
        Intent intent = new Intent(this, LifeCircleActivityB.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d(this.toString() + "  onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(this.toString() + "  onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(this.toString() + "  onDestroy()");
    }

}
