package com.duchen.design.decorator.beverage;

public class HouseBlend extends Beverage {

    @Override
    public String getDescription() {
        return "HouseBlend";
    }

    @Override
    public double cost() {
        return 0.89;
    }
}
