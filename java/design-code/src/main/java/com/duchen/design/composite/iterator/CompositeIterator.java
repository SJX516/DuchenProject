package com.duchen.design.composite.iterator;

import com.duchen.design.composite.MenuComponent.MenuComponent;
import com.duchen.design.composite.MenuComponent.MenuGroup;

import java.util.Iterator;
import java.util.Stack;

public class CompositeIterator implements Iterator {

    Stack<Iterator> mStack = new Stack<>();

    public CompositeIterator(Iterator iterator) {
        mStack.push(iterator);
    }

    @Override
    public boolean hasNext() {
        if (mStack.empty()) {
            return false;
        } else {
            Iterator iterator = mStack.peek();
            if (!iterator.hasNext()) {
                mStack.pop();
                return hasNext();
            } else {
                return true;
            }
        }
    }

    @Override
    public Object next() {
        if (hasNext()) {
            Iterator iterator = mStack.peek();
            MenuComponent menuComponent = (MenuComponent) iterator.next();
            if (menuComponent instanceof MenuGroup) {
                mStack.add(menuComponent.createIterator());
            }
            return menuComponent;
        } else {
            return null;
        }
    }
}
