package com.duchen.design.composite;

import com.duchen.design.composite.MenuComponent.MenuComponent;

public class WaitressComposite {

    MenuComponent allMenu;

    public WaitressComposite(MenuComponent allMenu) {
        this.allMenu = allMenu;
    }

    public void printMenu() {
        allMenu.print();
    }
}
