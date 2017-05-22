package com.duchen.design.composite;

import com.duchen.design.composite.MenuComponent.MenuComponent;

import java.util.Iterator;

public class WaitressComposite {

    MenuComponent allMenu;

    public WaitressComposite(MenuComponent allMenu) {
        this.allMenu = allMenu;
    }

    //由MenuComponent内部自行处理遍历,不具备扩展性
    public void printMenu() {
        allMenu.print();
    }

    //由MenuComponent提供一个迭代器,这样可以在外部处理遍历,自行添加筛选条件
    public void printVegetarianMenu() {
        Iterator iterator = allMenu.createIterator();
        System.out.println("\nVEGETARIAN MENU\n----");
        while (iterator.hasNext()) {
            MenuComponent menuComponent = (MenuComponent) iterator.next();
            try {
                if (menuComponent.isVegetarian()) {
                    menuComponent.print();
                }
            } catch (UnsupportedOperationException ignored) { }
        }
    }
}
