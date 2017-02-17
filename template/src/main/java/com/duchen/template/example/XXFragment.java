package com.duchen.template.example;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duchen.template.R;
import com.duchen.template.component.FragmentBase;
import com.duchen.template.concept.IViewModel;
import com.duchen.template.example.ui.XXListBox;
import com.duchen.template.example.ui.model.YYItemData;
import com.duchen.template.example.ui.model.ZZItemData;

public class XXFragment extends FragmentBase {

    private XXLogic mLogic;
    private XXListBox mListBox;

    private XXListBox.OnClickListener mOnClickListener = new XXListBox.OnClickListener() {

        @Override
        public void onClickInside(IViewModel itemData) {
            mLogic.onClickInside(itemData);
        }

        @Override
        public void onClickYYItem(YYItemData itemData) {
            mLogic.onClickYYItem(itemData);
        }

        @Override
        public void onClickZZItem(ZZItemData itemData) {
            mLogic.onClickZZItem(itemData);
        }
    };

    public static XXFragment newInstance() {
        return new XXFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frame_xxlist, null);
        findViews((ViewGroup) rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mLogic == null) {
            return;
        }
        initViews();
    }

    @Override
    public void prepareLogic() {
        mLogic = new XXLogic(getActivity(), mHandler);
    }

    @Override
    public void loadData() {
        super.loadData();
        mLogic.loadData();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case XXLogic.MSG_VIEWMODEL_UPDATE:
                mListBox.update();
                break;
        }
        return true;
    }

    private void findViews(ViewGroup rootView) {
        mListBox = (XXListBox) rootView.findViewById(R.id.xxframe_xxlist);
    }

    private void initViews() {
        mListBox.setOnClickListener(mOnClickListener);
        mListBox.bindViewModel(mLogic.getViewModel());
        mListBox.update();
    }

    @Override
    public void onDestroy() {
        mLogic.release();
        super.onDestroy();
    }
}
