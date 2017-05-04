package com.duchen.design.decorator.beverage.condiment.impl;

import com.duchen.design.decorator.beverage.Beverage;
import com.duchen.design.decorator.beverage.condiment.CondimentDecorator;

public class Soy extends CondimentDecorator {

    public Soy(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return "Soy, " + mBeverage.getDescription();
    }

    @Override
    public double cost() {
        return 0.15 + mBeverage.cost();
    }
}
