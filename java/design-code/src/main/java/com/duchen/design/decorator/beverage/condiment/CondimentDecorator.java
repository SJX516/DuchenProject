package com.duchen.design.decorator.beverage.condiment;

import com.duchen.design.decorator.beverage.Beverage;

public abstract class CondimentDecorator extends Beverage {

    protected Beverage mBeverage;

    public CondimentDecorator(Beverage beverage) {
        mBeverage = beverage;
    }

    @Override
    public abstract String getDescription();

}
