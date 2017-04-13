package com.duchen.template.usage.TestLifeCircle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.duchen.template.usage.AppActivityBase;
import com.duchen.template.usage.R;
import com.duchen.template.utils.LogUtil;

import java.util.ArrayList;

public class TestLifeCircleActivityB extends AppActivityBase {

    private ArrayList<Long> mLongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_lifecircle_b);
        findViewById(R.id.btn_jump).setOnClickListener(this);
        LogUtil.d(this.toString() + "  onCreate()");
        mLongs = new ArrayList<>(3000000);

    }

    @Override
    public void handleClick(int id, View v) {
        Intent intent = new Intent(this, TestLifeCircleActivityA.class);
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
