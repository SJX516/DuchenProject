package com.duchen.design.absFactory.pizza;

import com.duchen.design.absFactory.ingredient.pizzaIngredientFactory.PizzaIngredientFactory;
import com.duchen.design.factoryMethod.pizza.Pizza;

public class DCVeggiePizza extends Pizza {

    private PizzaIngredientFactory mIngredientFactory;

    public DCVeggiePizza(PizzaIngredientFactory ingredientFactory) {
        mIngredientFactory = ingredientFactory;
        name = "DC Style Veggie Pizza";
        dough = mIngredientFactory.createDough();
        sauce = mIngredientFactory.createSauce();
    }
}
