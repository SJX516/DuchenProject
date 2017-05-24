package com.duchen.design.absFactory;

import com.duchen.design.factoryMethod.pizza.Pizza;
import com.duchen.design.factoryMethod.pizzaStore.PizzaStore;

public class AbsFactoryTest implements Runnable {

    @Override
    public void run() {
        PizzaStore dcPizzaStore = new DCPizzaStore();
        Pizza pizza = dcPizzaStore.orderPizza("cheese");
        System.out.println("Duchen ordered a " + pizza.getName() + "\n");
    }
}
