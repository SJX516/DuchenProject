package com.duchen.template.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.duchen.template.R;

public class LoadingBox extends LinearLayout {

    private Drawable mNoContentDrawable;
    private Drawable mErrorDrawable;

    private Context mContext;
    protected ImageView mLoadingIcon;
    protected TextView mLoadingText;
    protected ProgressBar mLoadingProgress;

    private OnLoadingListener mLoadingListener;

    enum LoadingStatus {
        LOADING, ERROR, NO_CONTENT, HIDE
    }

    private LoadingStatus mLoadingStatus;

    private String mLoadingStr = "加载中...";
    private String mNoContentStr = "该内容不存在或已被删除";
    private String mLoadingErrorStr = "点击屏幕，重新加载";

    @ColorInt
    private int mTextColor = Color.parseColor("#ced1da");

    private boolean mCanReload = true;


    public LoadingBox(Context context) {
        this(context, null);
    }

    public LoadingBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public void init() {
        LayoutInflater.from(mContext).inflate(R.layout.box_loading_view, this);
        mLoadingIcon = (ImageView) findViewById(R.id.loading_icon);
        mLoadingText = (TextView) findViewById(R.id.loading_txt);
        mLoadingProgress = (ProgressBar) findViewById(R.id.loading_progress);
        onLoadingComplete();
    }

    public void setLoadingText(String s) {
        mLoadingStr = s;
    }

    public void setNoContentText(String s) {
        mNoContentStr = s;
    }

    public void setLoadingErrorText(String s) {
        mLoadingErrorStr = s;
    }

    public void setCanReload(boolean value) {
        mCanReload = value;
    }

    public void setNoContentIconDrawable(Drawable drawable) {
        mNoContentDrawable = drawable;
    }

    public void setErrorIconDrawable(Drawable drawable) {
        mErrorDrawable = drawable;
    }

    public void setTextColor(@ColorInt int color) {
        mTextColor = color;
    }

    protected void setStatus(LoadingStatus status) {
        mLoadingStatus = status;
        setOnClickListener(null);
        switch (status) {
            case LOADING:
                mLoadingIcon.setVisibility(View.GONE);
                mLoadingText.setVisibility(View.VISIBLE);
                mLoadingProgress.setVisibility(View.VISIBLE);
                mLoadingText.setText(mLoadingStr);
                setVisibility(View.VISIBLE);
                mLoadingText.setTextColor(mTextColor);
                break;
            case NO_CONTENT:
                if (mNoContentDrawable != null) {
                    mLoadingIcon.setImageDrawable(mNoContentDrawable);
                }
                mLoadingIcon.setVisibility(View.VISIBLE);
                mLoadingText.setVisibility(View.VISIBLE);
                mLoadingProgress.setVisibility(View.GONE);
                mLoadingText.setText(mNoContentStr);
                setVisibility(View.VISIBLE);
                mLoadingText.setTextColor(mTextColor);
                break;
            case ERROR:
                if (mErrorDrawable != null) {
                    mLoadingIcon.setImageDrawable(mErrorDrawable);
                }
                mLoadingIcon.setVisibility(View.VISIBLE);
                mLoadingText.setVisibility(View.VISIBLE);
                mLoadingProgress.setVisibility(View.GONE);
                mLoadingText.setText(mLoadingErrorStr);
                setVisibility(View.VISIBLE);
                setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mCanReload) {
                            load();
                        }
                    }
                });
                mLoadingText.setTextColor(mTextColor);
                break;
            case HIDE:
                mLoadingText.setVisibility(View.INVISIBLE);
                mLoadingProgress.setVisibility(View.INVISIBLE);
                setVisibility(View.GONE);
                mLoadingText.setTextColor(mTextColor);
                break;
            default:
                break;
        }
    }

    public boolean isLoading() {
        return mLoadingProgress.getVisibility() == View.VISIBLE;
    }

    public boolean isLoadingError() {
        return mLoadingStatus == LoadingStatus.ERROR;
    }

    public boolean isLoadingNoContent() {
        return mLoadingStatus == LoadingStatus.NO_CONTENT;
    }

    public void load() {
        if (isLoading()) {
            return;
        }
        setStatus(LoadingStatus.LOADING);
        if (!(mLoadingListener == null)) {
            mLoadingListener.onLoading();
        }
    }

    public void setOnLoadingListener(OnLoadingListener loadingListener) {
        mLoadingListener = loadingListener;
    }

    public interface OnLoadingListener {
        void onLoading();
    }


    public void onLoadingComplete() {
        setStatus(LoadingStatus.HIDE);
    }

    public void onNoContent(String tip) {
        setNoContentText(tip);
        onNoContent();
    }

    public void onNoContent() {
        setStatus(LoadingStatus.NO_CONTENT);
    }

    public void onLoadingError() {
        setStatus(LoadingStatus.ERROR);
    }
}
