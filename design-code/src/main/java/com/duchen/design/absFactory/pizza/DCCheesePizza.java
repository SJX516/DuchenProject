package com.duchen.design.absFactory.pizza;

import com.duchen.design.absFactory.ingredient.pizzaIngredientFactory.PizzaIngredientFactory;
import com.duchen.design.factoryMethod.pizza.Pizza;

public class DCCheesePizza extends Pizza {

    private PizzaIngredientFactory mIngredientFactory;

    public DCCheesePizza(PizzaIngredientFactory ingredientFactory) {
        mIngredientFactory = ingredientFactory;
        name = "DC Style Cheese Pizza";
        dough = mIngredientFactory.createDough();
        sauce = mIngredientFactory.createSauce();
    }
}
