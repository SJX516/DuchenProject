package com.duchen.design.templateMethod;

import com.duchen.design.templateMethod.impl.Coffee;
import com.duchen.design.templateMethod.impl.CoffeeWithHook;
import com.duchen.design.templateMethod.impl.Tea;

public class TemplateMethodTest implements Runnable {

    @Override
    public void run() {
        Tea tea = new Tea();
        System.out.println("\nMaking tea...");
        tea.prepareRecipe();
        Coffee coffee = new Coffee();
        System.out.println("\nMaking coffee...");
        coffee.prepareRecipe();
        System.out.println("\nMaking coffee with hook...");
        Coffee coffeeWithHook = new CoffeeWithHook();
        coffeeWithHook.prepareRecipe();
    }
}
