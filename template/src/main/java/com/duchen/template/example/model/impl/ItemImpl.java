package com.duchen.template.example.model.impl;

import com.duchen.template.example.model.Item;
import com.duchen.template.example.request.result.DataDto;

public class ItemImpl implements Item {

    private ItemType mItemType;
    private String mTitle;
    private String mDesc;
    private String mImgUrl;

    public ItemImpl(DataDto dataDto) {
        mItemType = computeItemType(dataDto.getType());
        mTitle = dataDto.getTitle();
        mDesc = dataDto.getDescription();
        mImgUrl = dataDto.getImageUrl();
    }

    private ItemType computeItemType(int type) {
        switch (type) {
            case 1:
                return ItemType.TYPE_YY_ITEM;
            case 2:
                return ItemType.TYPE_ZZ_ITEM;
            default:
                return ItemType.TYPE_YY_ITEM;
        }
    }

    @Override
    public ItemType getItemType() {
        return mItemType;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getDesc() {
        return mDesc;
    }

    @Override
    public String getImgUrl() {
        return mImgUrl;
    }
}
