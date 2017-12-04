package com.duchen.design.absFactory;

import com.duchen.design.absFactory.ingredient.pizzaIngredientFactory.PizzaIngredientFactory;
import com.duchen.design.absFactory.ingredient.pizzaIngredientFactory.impl.DCPizzaIngredientFactory;
import com.duchen.design.absFactory.pizza.DCCheesePizza;
import com.duchen.design.absFactory.pizza.DCVeggiePizza;
import com.duchen.design.factoryMethod.pizza.Pizza;
import com.duchen.design.factoryMethod.pizzaStore.PizzaStore;

/**
 * 抽象工厂使用对象组合,对象的创建被实现在工厂接口所暴露出来的方法中
 */
public class DCPizzaStore extends PizzaStore {

    @Override
    protected Pizza createPizza(String type) {
        Pizza pizza = null;
        PizzaIngredientFactory ingredientFactory = new DCPizzaIngredientFactory();
        if (type.equals("cheese")) {
            pizza = new DCCheesePizza(ingredientFactory);
        } else if (type.equals("veggie")) {
            pizza = new DCVeggiePizza(ingredientFactory);
        }
        return pizza;
    }
}
