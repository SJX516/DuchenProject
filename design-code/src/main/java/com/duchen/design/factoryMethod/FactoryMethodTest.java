package com.duchen.design.factoryMethod;

import com.duchen.design.factoryMethod.pizza.Pizza;
import com.duchen.design.factoryMethod.pizzaStore.PizzaStore;
import com.duchen.design.factoryMethod.pizzaStore.impl.ChicagoPizzaStore;
import com.duchen.design.factoryMethod.pizzaStore.impl.NYPizzaStore;

public class FactoryMethodTest implements Runnable {

    @Override
    public void run() {
        PizzaStore nyPizzaStore = new NYPizzaStore();
        PizzaStore chicagoPizzaStore = new ChicagoPizzaStore();
        Pizza pizza = nyPizzaStore.orderPizza("Veggie");
        System.out.println("Ethan ordered a " + pizza.getName() + "\n");
        pizza = chicagoPizzaStore.orderPizza("cheese");
        System.out.println("Joel ordered a " + pizza.getName() + "\n");
    }
}
