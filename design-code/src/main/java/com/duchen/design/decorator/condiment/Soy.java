package com.duchen.design.decorator.condiment;

import com.duchen.design.decorator.beverage.Beverage;

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
