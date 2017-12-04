package com.duchen.design.absFactory.ingredient.pizzaIngredientFactory;

import com.duchen.design.absFactory.ingredient.Dough.Dough;
import com.duchen.design.absFactory.ingredient.sauce.Sauce;

public interface PizzaIngredientFactory {

    Dough createDough();

    Sauce createSauce();

}
