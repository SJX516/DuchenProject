package com.duchen.design.compound;

import com.duchen.design.compound.observer.Observer;

public class QuackCounter implements Quackable {

    private Quackable mQuackable;
    private static int mCount = 0;

    public QuackCounter(Quackable quackable) {
        mQuackable = quackable;
    }

    public static int getCount() {
        return mCount;
    }

    @Override
    public String toString() {
        return mQuackable.toString();
    }

    @Override
    public void quack() {
        mCount++;
        mQuackable.quack();
    }

    @Override
    public void registerObserver(Observer observer) {
        mQuackable.registerObserver(observer);
    }

    @Override
    public void notifyObservers() {
        mQuackable.notifyObservers();
    }
}
