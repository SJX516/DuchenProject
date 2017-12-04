package com.duchen.template.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.duchen.template.R;

public abstract class BaseDialog implements View.OnClickListener {

    private AlertDialog mDialog;
    private View mContentView;
    private View mCancelView;
    private View mConfirmView;
    private OnDialogResultListener mListener;

    protected int mCancelViewId = -1;
    protected int mConfirmViewId = -1;

    public interface OnDialogResultListener {
        void onCancel();
        void onConfirm();
        void onEvent(int event, Object data);
    }

    public enum Type {
        Normal, Bottom
    }

    public void show(Context context, OnDialogResultListener listener) {
        mListener = listener;
        show(context, getType());
    }

    private void show(Context context, Type type) {
        int styleId = R.style.normal_dialog;
        if (type == Type.Bottom) {
            styleId = R.style.bottom_dialog;
        }
        mDialog = new AlertDialog.Builder(context, styleId).create();
        mDialog.show();
        mContentView = View.inflate(context, getContentView(), null);
        Window window = mDialog.getWindow();
        window.setContentView(mContentView);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();

        if (type == Type.Bottom) {
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.BOTTOM;
        } else {
            lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        window.setAttributes(lp);
        init(mContentView);
    }

    private void init(View contentView) {
        initView(contentView);
        mCancelView = contentView.findViewById(mCancelViewId);
        mConfirmView = contentView.findViewById(mConfirmViewId);
        if (mCancelView != null) {
            mCancelView.setOnClickListener(this);
        }
        if (mConfirmView != null) {
            mConfirmView.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mCancelViewId) {
            dismiss();
            if (mListener != null) {
                mListener.onCancel();
            }
        } else if (v.getId() == mConfirmViewId) {
            dismiss();
            if (mListener != null) {
                mListener.onConfirm();
            }
        } else {
            onInsideClick(v);
        }
    }

    protected abstract int getContentView();

    protected Type getType() {
        return Type.Normal;
    }

    protected abstract void initView(View content);

    protected abstract void onInsideClick(View view);

    public void dismiss() {
        mDialog.dismiss();
    }

    public void onEvent(int event, Object data) {
        if (mListener != null) {
            mListener.onEvent(event, data);
        }
    }

}
