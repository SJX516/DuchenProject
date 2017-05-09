package com.duchen.design.iterator.menu;


import com.duchen.design.iterator.iterator.DinerMenuIterator;

import java.util.Iterator;

public class DinerMenu implements Menu {
    static final int MAX_ITEMS = 6;
    int mNumOfItems = 0;
    MenuItem[] mMenuItems;

    public DinerMenu() {
        mMenuItems = new MenuItem[MAX_ITEMS];
        addItem("Vegetarian BLT", "Bacon with lettuce & tomato on whole wheat", true, 2.99);
        addItem("BLT", "Bacon with lettuce & tomato on whole wheat", false, 2.99);
        addItem("Soup of the day", "with a side of potato salad", false, 3.29);
        addItem("Hotdog", "A hot dog, with relish, onions, topped with cheese", false, 3.05);
    }

    public void addItem(String name, String desc, boolean vegetarian, double price) {
        MenuItem menuItem = new MenuItem(name, desc, vegetarian, price);
        if (mNumOfItems >= MAX_ITEMS) {
            System.err.println("Sorry, menu is full! can't add item to menu");
        } else {
            mMenuItems[mNumOfItems] = menuItem;
            mNumOfItems += 1;
        }
    }

    @Override
    public Iterator<MenuItem> createIterator() {
        return new DinerMenuIterator(mMenuItems);
    }
}
