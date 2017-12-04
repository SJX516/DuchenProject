package com.duchen.design.iterator;

import com.duchen.design.iterator.menu.DinerMenu;
import com.duchen.design.iterator.menu.Menu;
import com.duchen.design.iterator.menu.PancakeHouseMenu;

public class IteratorTest implements Runnable {
    @Override
    public void run() {
        Menu breakfastMenu = new PancakeHouseMenu();
        Menu launchMenu = new DinerMenu();
        Waitress waitress = new Waitress(breakfastMenu, launchMenu);
        waitress.printMenu();
    }
}
