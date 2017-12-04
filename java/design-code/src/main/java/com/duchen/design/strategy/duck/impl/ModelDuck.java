package com.duchen.design.strategy.duck.impl;

import com.duchen.design.strategy.behavior.MuteQuack;
import com.duchen.design.strategy.behavior.impl.FlyNoWay;
import com.duchen.design.strategy.duck.Duck;

public class ModelDuck extends Duck {

    public ModelDuck() {
        mFlyBehavior = new FlyNoWay();
        mQuackBehavior = new MuteQuack();
    }

    @Override
    public void display() {
        System.out.println("I'm a model duck");
    }
}
