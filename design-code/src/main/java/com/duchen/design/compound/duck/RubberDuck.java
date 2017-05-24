package com.duchen.design.compound.duck;

import com.duchen.design.compound.Quackable;
import com.duchen.design.compound.observer.Observer;
import com.duchen.design.compound.observer.QuackObservable;

public class RubberDuck implements Quackable {

    private QuackObservable mObservable;

    public RubberDuck() {
        mObservable = new QuackObservable(this);
    }

    @Override
    public void registerObserver(Observer observer) {
        mObservable.registerObserver(observer);
    }

    @Override
    public void notifyObservers() {
        mObservable.notifyObservers();
    }

    @Override
    public void quack() {
        System.out.println("Squeak");
        notifyObservers();
    }

    @Override
    public String toString() {
        return "Rubber Duck";
    }
}
