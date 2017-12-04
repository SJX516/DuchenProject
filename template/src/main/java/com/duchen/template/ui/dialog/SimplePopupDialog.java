package com.duchen.template.ui.dialog;

import android.view.View;

public class SimplePopupDialog extends BaseDialog {

    private int mLayoutId;

    public SimplePopupDialog(int layoutId) {
        mLayoutId = layoutId;
    }

    @Override
    protected Type getType() {
        return Type.Bottom;
    }

    @Override
    protected int getContentView() {
        return mLayoutId;
    }

    @Override
    protected void initView(View content) {

    }

    @Override
    protected void onInsideClick(View view) {

    }
}
