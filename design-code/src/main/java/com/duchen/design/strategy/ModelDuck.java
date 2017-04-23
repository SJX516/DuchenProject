package com.duchen.design.strategy;

import com.duchen.design.strategy.behavior.FlyNoWay;
import com.duchen.design.strategy.behavior.MuteQuack;

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
