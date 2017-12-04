package com.duchen.design.iterator.menu;

import java.util.ArrayList;
import java.util.Iterator;

public class PancakeHouseMenu implements Menu {
    ArrayList<MenuItem> mMenuItems;

    public PancakeHouseMenu() {
        mMenuItems = new ArrayList<>();
        addItem("K&B's Pancake Breakfast", "Pancake with fried eggs, toast", true, 2.99);
        addItem("Regular Pancake Breakfast", "Pancake with fried eggs, sausage", false, 2.99);
        addItem("Blueberry Breakfast", "Pancake made with fresh blueberries", true, 3.49);
        addItem("Waffles", "Waffles, with your choice of blueberries or strawberries", true, 3.59);
    }

    public void addItem(String name, String desc, boolean vegetarian, double price) {
        MenuItem menuItem = new MenuItem(name, desc, vegetarian, price);
        mMenuItems.add(menuItem);
    }

    @Override
    public Iterator<MenuItem> createIterator() {
        return mMenuItems.iterator();
    }
}
