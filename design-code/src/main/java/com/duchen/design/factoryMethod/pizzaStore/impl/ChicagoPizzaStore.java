package com.duchen.design.factoryMethod.pizzaStore.impl;

import com.duchen.design.factoryMethod.pizza.Pizza;
import com.duchen.design.factoryMethod.pizza.impl.ChicagoStyleCheesePizza;
import com.duchen.design.factoryMethod.pizzaStore.PizzaStore;

public class ChicagoPizzaStore extends PizzaStore{

    @Override
    protected Pizza createPizza(String type) {
        return new ChicagoStyleCheesePizza();
    }

}
