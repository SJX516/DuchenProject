package com.duchen.design.decorator.beverage.impl;

import com.duchen.design.decorator.beverage.Beverage;

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
