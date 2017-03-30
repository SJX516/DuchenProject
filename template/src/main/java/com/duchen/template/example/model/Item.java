package com.duchen.template.example.model;

public interface Item {

    enum ItemType {
        TYPE_YY_ITEM, TYPE_ZZ_ITEM;
    }

    ItemType getItemType();

    String getTitle();

    String getDesc();

    String getImgUrl();

}
