package com.duchen.design.factoryMethod.pizza.impl;

import com.duchen.design.absFactory.ingredient.Dough.impl.ThickCrustDough;
import com.duchen.design.absFactory.ingredient.sauce.impl.TomatoSauce;
import com.duchen.design.factoryMethod.pizza.Pizza;

public class NYStyleVeggiePizza extends Pizza {

    public NYStyleVeggiePizza() {
        name = "NY Style Sauce and Veggie Pizza";
        dough = new ThickCrustDough();
        sauce = new TomatoSauce();
    }
}
