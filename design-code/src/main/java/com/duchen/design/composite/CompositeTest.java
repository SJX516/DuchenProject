package com.duchen.design.composite;

import com.duchen.design.composite.MenuComponent.MenuComponent;
import com.duchen.design.composite.MenuComponent.MenuGroup;
import com.duchen.design.composite.MenuComponent.MenuItem;

public class CompositeTest implements Runnable {
    @Override
    public void run() {
        MenuComponent pancakeHouseMenu = new MenuGroup("PANCAKE HOUSE MENU", "Breakfast");
        MenuComponent dinerMenu = new MenuGroup("DINER MENU", "Launch");
        MenuComponent cafeMenu = new MenuGroup("CAFE MENU", "Dinner");
        MenuComponent dessertMenu = new MenuGroup("DESSERT MENU", "Dessert of course!");

        pancakeHouseMenu.add(new MenuItem("K&B's Pancake Breakfast", "Pancake with fried eggs, toast", true, 2.99));
        pancakeHouseMenu.add(new MenuItem("Regular Pancake Breakfast", "Pancake with fried eggs, sausage", false, 2.99));
        pancakeHouseMenu.add(new MenuItem("Blueberry Breakfast", "Pancake made with fresh blueberries", true, 3.49));
        pancakeHouseMenu.add(new MenuItem("Waffles", "Waffles, with your choice of blueberries or strawberries", true, 3.59));

        dinerMenu.add(new MenuItem("Vegetarian BLT", "Bacon with lettuce & tomato on whole wheat", true, 2.99));
        dinerMenu.add(new MenuItem("BLT", "Bacon with lettuce & tomato on whole wheat", false, 2.99));
        dinerMenu.add(new MenuItem("Soup of the day", "with a side of potato salad", false, 3.29));
        dinerMenu.add(new MenuItem("Hotdog", "A hot dog, with relish, onions, topped with cheese", false, 3.05));

        cafeMenu.add(new MenuItem("Veggie Burger and Air Fries", "Veggie burger on a whole wheat bun, lettuce", true, 3.99));
        cafeMenu.add(new MenuItem("Soup of the day", "A cup of the soup of the day, with a side salad", false, 3.69));
        cafeMenu.add(new MenuItem("Burrito", "A large burrito, with whole pinto beans, salsa, guacamole", true, 4.29));

        dessertMenu.add(new MenuItem("Apple pie", "Apple pie with flaky crust, topped with vanilla ice cream", true, 1.59));

        MenuComponent allMenu = new MenuGroup("ALL MENUS", "All menus combined");
        allMenu.add(pancakeHouseMenu);
        allMenu.add(dinerMenu);
        allMenu.add(cafeMenu);

        dinerMenu.add(dessertMenu);

        WaitressComposite waitressComposite = new WaitressComposite(allMenu);
        waitressComposite.printVegetarianMenu();

    }
}
