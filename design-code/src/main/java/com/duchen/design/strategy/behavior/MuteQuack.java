package com.duchen.design.strategy.behavior;

public class MuteQuack implements com.duchen.design.strategy.behavior.impl.QuackBehavior {

    @Override
    public void quack() {
        System.out.println("<< Silence >>");
    }
}
