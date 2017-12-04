package com.duchen.design.composite.MenuComponent;

import com.duchen.design.composite.iterator.CompositeIterator;

import java.util.ArrayList;
import java.util.Iterator;

public class MenuGroup extends MenuComponent {

    ArrayList<MenuComponent> mMenuComponents = new ArrayList<>();
    String mName;
    String mDesc;

    public MenuGroup(String name, String desc) {
        mName = name;
        mDesc = desc;
    }

    @Override
    public void add(MenuComponent menuComponent) {
        mMenuComponents.add(menuComponent);
    }

    @Override
    public void remove(MenuComponent menuComponent) {
        mMenuComponents.remove(menuComponent);
    }

    @Override
    public MenuComponent getChild(int i) {
        return mMenuComponents.get(i);
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
    public void print() {
        System.out.print("\n" + getName());
        System.out.println(", " + getDesc());
        System.out.println("--------------");
        for (MenuComponent menuComponent : mMenuComponents) {
            menuComponent.print();
        }
    }

    @Override
    public Iterator createIterator() {
        return new CompositeIterator(mMenuComponents.iterator());
    }
}
