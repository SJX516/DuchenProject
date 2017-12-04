package com.duchen.design.iterator.menu;

public class MenuItem {

    String mName;
    String mDesc;
    boolean mVegetarian;
    double mPrice;

    public MenuItem(String name, String desc, boolean vegetarian, double price) {
        mName = name;
        mDesc = desc;
        mVegetarian = vegetarian;
        mPrice = price;
    }

    public String getName() {
        return mName;
    }

    public String getDesc() {
        return mDesc;
    }

    public boolean isVegetarian() {
        return mVegetarian;
    }

    public double getPrice() {
        return mPrice;
    }
}
