package com.duchen.template.example.ui.model;

import android.text.TextUtils;

import com.duchen.template.concept.IViewModel;

public class YYItemData implements IViewModel {

    private String mTitle;
    private String mDescription;
    private boolean mIsSelected = false;
    private boolean mIsShowSomething = true;

    public YYItemData() {

    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean selected) {
        mIsSelected = selected;
    }

    public boolean isShowSomething() {
        return mIsShowSomething;
    }

    public void setShowSomething(boolean showSomething) {
        mIsShowSomething = showSomething;
    }
}
