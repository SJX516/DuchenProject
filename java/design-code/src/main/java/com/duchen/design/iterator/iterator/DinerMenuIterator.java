package com.duchen.design.iterator.iterator;

import com.duchen.design.iterator.menu.MenuItem;

import java.util.Iterator;

public class DinerMenuIterator implements Iterator<MenuItem> {

    MenuItem[] mItems;
    int mPosition = 0;

    public DinerMenuIterator(MenuItem[] items) {
        mItems = items;
    }

    @Override
    public boolean hasNext() {
        if (mPosition >= mItems.length || mItems[mPosition] == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public MenuItem next() {
        MenuItem menuItem = mItems[mPosition];
        mPosition++;
        return menuItem;
    }
}
