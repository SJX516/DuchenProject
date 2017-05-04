package com.duchen.design.strategy.behavior.impl;

import com.duchen.design.strategy.behavior.FlyBehavior;

public class FlyWithWings implements FlyBehavior {

    @Override
    public void fly() {
        System.out.println("I'm flying!!");
    }
}
