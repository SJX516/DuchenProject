package com.duchen.design.iterator.menu;

import java.util.Iterator;

public interface Menu {
    Iterator<MenuItem> createIterator();
}
