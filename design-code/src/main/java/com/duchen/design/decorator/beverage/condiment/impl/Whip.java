package com.duchen.design.decorator.beverage.condiment.impl;

import com.duchen.design.decorator.beverage.Beverage;
import com.duchen.design.decorator.beverage.condiment.CondimentDecorator;

public class Whip extends CondimentDecorator {

    public Whip(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return "Whip, " + mBeverage.getDescription();
    }

    @Override
    public double cost() {
        return 0.10 + mBeverage.cost();
    }
}
