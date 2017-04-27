package com.duchen.design.decorator.condiment;

import com.duchen.design.decorator.beverage.Beverage;

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
