package com.duchen.design.composite.MenuComponent;

import com.duchen.design.composite.iterator.NullIterator;

import java.util.Iterator;

public class MenuItem extends MenuComponent {

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

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getDesc() {
        return mDesc;
    }

    @Override
    public boolean isVegetarian() {
        return mVegetarian;
    }

    @Override
    public double getPrice() {
        return mPrice;
    }

    @Override
    public void print() {
        System.out.print("  " + getName());
        if (isVegetarian()) {
            System.out.print("(v)");
        }
        System.out.println(", " + getPrice());
        System.out.println("    --" + getDesc());
    }

    @Override
    public Iterator createIterator() {
        return new NullIterator();
    }
}
