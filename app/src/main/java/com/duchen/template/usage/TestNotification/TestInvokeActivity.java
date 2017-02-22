package com.duchen.template.usage.TestNotification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.duchen.template.usage.AppActivityBase;
import com.duchen.template.usage.R;

public class TestInvokeActivity extends AppActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_invoke);
    }

    @Override
    public void handleClick(int id, View v) {

    }
}
