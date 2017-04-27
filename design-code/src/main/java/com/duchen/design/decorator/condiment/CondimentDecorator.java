package com.duchen.design.decorator.condiment;

import com.duchen.design.decorator.beverage.Beverage;

public abstract class CondimentDecorator extends Beverage {

    Beverage mBeverage;

    public CondimentDecorator(Beverage beverage) {
        mBeverage = beverage;
    }

    @Override
    public abstract String getDescription();

}
