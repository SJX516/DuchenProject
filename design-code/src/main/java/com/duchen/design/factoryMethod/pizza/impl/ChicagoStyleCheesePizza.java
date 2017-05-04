package com.duchen.design.factoryMethod.pizza.impl;

import com.duchen.design.absFactory.ingredient.Dough.impl.ThinCrustDough;
import com.duchen.design.absFactory.ingredient.sauce.impl.MarinaraSauce;
import com.duchen.design.factoryMethod.pizza.Pizza;

public class ChicagoStyleCheesePizza extends Pizza {

    public ChicagoStyleCheesePizza() {
        name = "Chicago Style Deep Dish Cheese Pizza";
        dough = new ThinCrustDough();
        sauce = new MarinaraSauce();
    }

    @Override
    public void cut() {
        System.out.println("Cutting the pizza into square slices");
    }
}
