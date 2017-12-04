package com.duchen.design.factoryMethod.pizzaStore;

import com.duchen.design.factoryMethod.pizza.Pizza;

/**
 * 工厂方法使用继承,把对象的创建委托给子类,子类实现工厂方法来创建对象
 */
public abstract class PizzaStore {

    public Pizza orderPizza(String type) {
        Pizza pizza;

        pizza = createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }

    protected abstract Pizza createPizza(String type);
}
