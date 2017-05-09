package com.duchen.design.iterator;

import com.duchen.design.iterator.menu.Menu;
import com.duchen.design.iterator.menu.MenuItem;

import java.util.Iterator;

public class Waitress {
    Menu mBreakfastMenu;
    Menu mLaunchMenu;

    public Waitress(Menu breakfastMenu, Menu launchMenu) {
        mBreakfastMenu = breakfastMenu;
        mLaunchMenu = launchMenu;
    }

    public void printMenu() {
        Iterator breakfastIterator = mBreakfastMenu.createIterator();
        Iterator launchIterator = mLaunchMenu.createIterator();
        System.out.println("MENU\n----\nBREAKFAST");
        printMenu(breakfastIterator);
        System.out.println("\nLAUNCH");
        printMenu(launchIterator);
    }

    public void printMenu(Iterator iterator) {
        while (iterator.hasNext()) {
            MenuItem menuItem = (MenuItem) iterator.next();
            System.out.print(menuItem.getName() + ", ");
            System.out.print(menuItem.getPrice() + " -- ");
            System.out.println(menuItem.getDesc());
        }
    }
}
