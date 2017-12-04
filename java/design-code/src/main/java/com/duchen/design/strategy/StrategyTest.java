package com.duchen.design.strategy;

import com.duchen.design.strategy.behavior.impl.FlyWithWings;
import com.duchen.design.strategy.duck.Duck;
import com.duchen.design.strategy.duck.impl.ModelDuck;

public class StrategyTest implements Runnable {

    @Override
    public void run() {
        Duck duck = new ModelDuck();
        duck.display();
        duck.performQuack();
        duck.performFly();
        duck.setFlyBehavior(new FlyWithWings());
        duck.performFly();
    }
}
