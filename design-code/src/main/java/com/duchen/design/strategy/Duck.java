package com.duchen.design.strategy;

import com.duchen.design.strategy.behavior.FlyBehavior;
import com.duchen.design.strategy.behavior.QuackBehavior;

/**
 * 策略模式 定义了算法族，分别封装起来，让它们之间可以互相替换，此模式让算法的变化独立于使用算法的客户
 */
public abstract class Duck {

    FlyBehavior mFlyBehavior;
    QuackBehavior mQuackBehavior;

    public abstract void display();

    public void setFlyBehavior(FlyBehavior flyBehavior) {
        mFlyBehavior = flyBehavior;
    }

    public void setQuackBehavior(QuackBehavior quackBehavior) {
        mQuackBehavior = quackBehavior;
    }

    public void performFly() {
        mFlyBehavior.fly();
    }

    public void performQuack() {
        mQuackBehavior.quack();
    }

    public void swim() {
        System.out.println("All ducks float!");
    }
}
