package com.duchen.design.strategy.behavior.impl;

public class NormalQuack implements QuackBehavior {

    @Override
    public void quack() {
        System.out.println("Quack");
    }
}
