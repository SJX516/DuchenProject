package com.duchen.template.usage.launcher;

import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.duchen.template.usage.AppActivityBase;
import com.duchen.template.usage.MainActivity;
import com.duchen.template.usage.R;

public class LauncherActivity extends AppActivityBase {

    private Button mSettingBtn;
    private Button mMainBtn;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_launcher);
    }

    @Override
    public void findViews() {
        mSettingBtn = (Button) findViewById(R.id.btn_setting);
        mMainBtn = (Button) findViewById(R.id.btn_main);
    }

    @Override
    public void initViews() {
        mSettingBtn.setOnClickListener(this);
        mMainBtn.setOnClickListener(this);
    }

    @Override
    public void handleClick(int id, View v) {
        switch (id) {
            case R.id.btn_setting:
                launchSetting();
                break;
            case R.id.btn_main:
                launchMain();
                break;
        }
    }

    private void launchSetting() {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        startActivity(intent);
    }

    private void launchMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
