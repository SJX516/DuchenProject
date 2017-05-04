package com.duchen.design.factoryMethod.pizza;

import com.duchen.design.absFactory.ingredient.Dough.Dough;
import com.duchen.design.absFactory.ingredient.sauce.Sauce;

public abstract class Pizza {

    protected String name;
    protected Dough dough;
    protected Sauce sauce;

    public void prepare() {
        System.out.println("Preparing " + name);
        System.out.println("Tossing dough: " + dough.getName());
        System.out.println("Adding sauce: " + sauce.getName());
    }

    public void bake() {
        System.out.println("Bake for 25 minutes at 350");
    }

    public void cut() {
        System.out.println("Cutting the pizza into diagonal slices");
    }

    public void box() {
        System.out.println("Place pizza in official PizzaStore box");
    }

    public String getName() {
        return name;
    }
}
