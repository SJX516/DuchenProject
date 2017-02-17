package com.duchen.template.example.ui.model;

import com.duchen.template.concept.IViewModel;


public class ZZItemData implements IViewModel{

    private String mImageUrl;

    public ZZItemData() {

    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
