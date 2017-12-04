package com.duchen.design.decorator.beverage.condiment.impl;

import com.duchen.design.decorator.beverage.Beverage;
import com.duchen.design.decorator.beverage.condiment.CondimentDecorator;

public class Mocha extends CondimentDecorator {

    public Mocha(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return "Mocha, " + mBeverage.getDescription();
    }

    @Override
    public double cost() {
        return 0.20 + mBeverage.cost();
    }
}
