package com.duchen.template.example.frame;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duchen.template.R;
import com.duchen.template.component.FragmentBase;
import com.duchen.template.concept.IViewModel;
import com.duchen.template.example.box.XXListBox;
import com.duchen.template.example.box.model.YYItemData;
import com.duchen.template.example.box.model.ZZItemData;
import com.duchen.template.example.logic.XXLogic;
import com.duchen.template.example.logic.impl.XXLogicImpl;
import com.duchen.template.utils.ToastUtil;

public class XXFrame extends FragmentBase {

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

    public static XXFrame newInstance() {
        return new XXFrame();
    }

    @Override
    public void prepareLogic() {
        mLogic = new XXLogicImpl(getActivity(), mHandler);
    }

    @Override
    public View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frame_xxlist, null);
    }

    @Override
    public void findViews(View rootView) {
        mListBox = (XXListBox) rootView.findViewById(R.id.xxframe_xxlist);
    }

    @Override
    public void initViews() {
        mListBox.setOnClickListener(mOnClickListener);
        mListBox.bindViewModel(mLogic.getViewModel());
        mListBox.update();
    }


    @Override
    public void loadData() {
        super.loadData();
        //loadingView start (show)
        mLogic.loadData(true);
    }

    public void loadMore() {
        mLogic.loadData(false);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case XXLogicImpl.MSG_LOAD_DATA_SUCCESS:
                //loadingView complete (hide)
                mListBox.update();
                break;
            case XXLogicImpl.MSG_LOAD_MORE_FAILED:
                ToastUtil.showToast("获取数据失败");
                break;
            case XXLogicImpl.MSG_LOAD_DATA_NO_CONTENT:
                //loadingView showNoContent
                mListBox.setVisibility(View.INVISIBLE);
                break;
            case XXLogicImpl.MSG_LOAD_DATA_ERROR:
                //loadingView showError
                mListBox.setVisibility(View.INVISIBLE);
                break;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        mLogic.release();
        super.onDestroy();
    }
}
