package com.duchen.design.absFactory.ingredient.pizzaIngredientFactory.impl;

import com.duchen.design.absFactory.ingredient.Dough.Dough;
import com.duchen.design.absFactory.ingredient.Dough.impl.ThickCrustDough;
import com.duchen.design.absFactory.ingredient.pizzaIngredientFactory.PizzaIngredientFactory;
import com.duchen.design.absFactory.ingredient.sauce.Sauce;
import com.duchen.design.absFactory.ingredient.sauce.impl.TomatoSauce;

public class DCPizzaIngredientFactory implements PizzaIngredientFactory {

    @Override
    public Dough createDough() {
        return new ThickCrustDough();
    }

    @Override
    public Sauce createSauce() {
        return new TomatoSauce();
    }
}
