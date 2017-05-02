package com.duchen.template.component;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duchen.template.concept.IFrame;

import de.greenrobot.event.EventBus;

public abstract class FragmentBase extends Fragment implements Handler.Callback, IFrame {

    private static final String TAG = FragmentBase.class.getCanonicalName();

    private boolean mIsLoaded = false;
    protected Handler mHandler;
    protected EventBus mEventBus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mHandler = new Handler(this);
        mEventBus = EventBus.getDefault();
        handleArguments(getArguments());
        prepareLogic();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View root = getContentView(inflater, container, savedInstanceState);
        findViews(root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    public abstract View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public abstract void findViews(View root);

    public abstract void initViews();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            onVisibilityChangedInViewPager(isVisibleToUser);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void handleArguments(Bundle arguments) {
    }

    //是否已经成功加载出数据
    public boolean isLoaded() {
        return mIsLoaded;
    }

    public void onLoadSuccess() {
        //不需要onLoadError，只记录该界面第一次load数据状态，一定成功一次，就不需要使用load这个状态了，而是refresh
        mIsLoaded = true;
    }

    //加载数据，显示加载界面
    public void loadData() {

    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    protected void onVisibilityChangedInViewPager(boolean isVisibleToUser) {

    }
}
