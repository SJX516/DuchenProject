package com.duchen.design.strategy.behavior.impl;

import com.duchen.design.strategy.behavior.FlyBehavior;

public class FlyNoWay implements FlyBehavior {

    @Override
    public void fly() {
        System.out.println("I can't fly");
    }
}
