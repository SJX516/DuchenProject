package com.duchen.template.component;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.duchen.template.component.helper.FrameworkActivityManager;
import com.duchen.template.concept.IActivity;
import com.duchen.template.concept.model.LegalModelParser;
import com.duchen.template.scope.TemplateScopeInstance;
import com.duchen.template.utils.LogUtil;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public abstract class ActivityBase extends AppCompatActivity implements IActivity, Handler.Callback {

    private static final String TAG = "ActivityBase";

    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    protected LayoutInflater mInflater;

    protected Handler mHandler;

    protected EventBus mEventBus;

    protected LegalModelParser mParser;

    protected android.support.v7.app.ActionBar mActionBar;

    protected boolean mIsResumeStatus = false;
    protected boolean mIsDestroyStatus = true;
    protected ArrayList<Integer> mRequestIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mHandler = new Handler(this);
        handleIntent(getIntent());
        prepareLogic();

        super.onCreate(savedInstanceState);
        LogUtil.d(this.getClass().getSimpleName(), "onCreate task id = " + this.getTaskId());
        mIsDestroyStatus = false;
        FrameworkActivityManager.getInstance().addActivity(this);

        mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        mEventBus = EventBus.getDefault();
        mParser = new LegalModelParser();
        mRequestIdList = new ArrayList<>();
        initActionBar();
    }

    @Override
    protected void onStart() {
        LogUtil.d(TAG, "onStart");
        super.onStart();
    }

    protected void initActionBar() {
        mActionBar = getSupportActionBar();
    }

    protected void hideActionBar() {
        if (mActionBar != null) {
            mActionBar.hide();
        }
    }

    public void onAttachFragment(Fragment fragment) {
        if (null != mFragmentList) {
            if (mFragmentList.contains(fragment)) {
                return;
            }
            mFragmentList.add(fragment);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsResumeStatus = true;
    }

    @Override
    protected void onPause() {
        mIsResumeStatus = false;
        super.onPause();
    }

    public boolean isResumeStatus() {
        return mIsResumeStatus;
    }

    @Override
    protected void onDestroy() {
        FrameworkActivityManager.getInstance().removeActivity(this);
        mIsDestroyStatus = true;
        super.onDestroy();
        if (null != mFragmentList) {
            mFragmentList.clear();
        }
        mFragmentList = null;
    }

    public boolean isDestroyedState() {
        return mIsDestroyStatus;
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean mbBackKeyDown = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mbBackKeyDown = true;
            return true;
        }
        mbBackKeyDown = false;
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mbBackKeyDown) {
                onBackPressed();
            }
            mbBackKeyDown = false;
            return true;
        }
        mbBackKeyDown = false;
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void handleIntent(Intent intent) {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void prepareLogic() {

    }

    @Override
    public boolean launchMainActivityOnFinish() {
        return false;
    }

    @Override
    public void finish() {
        super.finish();
        if (launchMainActivityOnFinish()) {
            if (TemplateScopeInstance.getInstance().isMainActivityDestroyed()) {
                TemplateScopeInstance.getInstance().launchNewMainActivity(this);
            }
        }
    }
}
