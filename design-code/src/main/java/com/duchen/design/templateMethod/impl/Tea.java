package com.duchen.design.templateMethod.impl;

import com.duchen.design.templateMethod.CaffeineBeverage;

public class Tea extends CaffeineBeverage {

    @Override
    protected void brew() {
        System.out.println("Steeping the tea");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Adding Lemon");
    }
}
