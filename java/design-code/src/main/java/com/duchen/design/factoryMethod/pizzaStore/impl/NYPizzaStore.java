package com.duchen.design.factoryMethod.pizzaStore.impl;

import com.duchen.design.factoryMethod.pizza.Pizza;
import com.duchen.design.factoryMethod.pizza.impl.NYStyleCheesePizza;
import com.duchen.design.factoryMethod.pizza.impl.NYStyleVeggiePizza;
import com.duchen.design.factoryMethod.pizzaStore.PizzaStore;

public class NYPizzaStore extends PizzaStore{

    @Override
    protected Pizza createPizza(String type) {
        if (type.equals("cheese")) {
            return new NYStyleCheesePizza();
        } else if (type.equals("veggie")) {
            return new NYStyleVeggiePizza();
        }
        return new NYStyleCheesePizza();
    }

}
