package com.duchen.template.usage.TestLifeCircle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.duchen.template.usage.AppActivityBase;
import com.duchen.template.usage.R;
import com.duchen.template.utils.LogUtil;

import java.util.ArrayList;

public class LifeCircleActivityB extends AppActivityBase {

    private ArrayList<Long> mLongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(this.toString() + "  onCreate()");

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_test_lifecircle_b);
    }

    @Override
    public void findViews() {
        findViewById(R.id.btn_jump).setOnClickListener(this);
    }

    @Override
    public void initViews() {
        mLongs = new ArrayList<>(3000000);
    }

    @Override
    public void handleClick(int id, View v) {
        Intent intent = new Intent(this, LifeCircleActivityA.class);
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
