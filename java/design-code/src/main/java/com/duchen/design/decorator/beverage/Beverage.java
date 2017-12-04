package com.duchen.design.decorator.beverage;

public abstract class Beverage {
    String mDescription = "Unknown Beverage";

    public String getDescription() {
        return mDescription;
    }

    public abstract double cost();
}
