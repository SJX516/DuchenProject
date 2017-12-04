package com.duchen.design.templateMethod.impl;

import com.duchen.design.templateMethod.CaffeineBeverage;

public class Coffee extends CaffeineBeverage {

    @Override
    protected void brew() {
        System.out.println("Dripping Coffee through filter");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Adding Sugar and Milk");
    }
}
