package com.duchen.design.decorator.condiment;

import com.duchen.design.decorator.beverage.Beverage;

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
