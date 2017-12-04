package com.duchen.design.composite.MenuComponent;

import com.duchen.design.composite.iterator.NullIterator;

import java.util.Iterator;

public abstract class MenuComponent {

    public void add(MenuComponent menuComponent) {
        throw new UnsupportedOperationException("add");
    }

    public void remove(MenuComponent menuComponent) {
        throw new UnsupportedOperationException("remove");
    }

    public MenuComponent getChild(int i) {
        throw new UnsupportedOperationException("getChild");
    }

    public String getName() {
        throw new UnsupportedOperationException("getName");
    }

    public String getDesc() {
        throw new UnsupportedOperationException("getDesc");
    }

    public boolean isVegetarian() {
        throw new UnsupportedOperationException("isVegetarian");
    }

    public double getPrice() {
        throw new UnsupportedOperationException("getPrice");
    }

    public void print() {
        throw new UnsupportedOperationException("print");
    }

    public Iterator createIterator() {
        return new NullIterator();
    }
}
