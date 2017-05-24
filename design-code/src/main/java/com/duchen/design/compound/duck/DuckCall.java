package com.duchen.design.compound.duck;

import com.duchen.design.compound.Quackable;
import com.duchen.design.compound.observer.Observer;
import com.duchen.design.compound.observer.QuackObservable;

public class DuckCall implements Quackable {

    QuackObservable mObservable;

    public DuckCall() {
        mObservable = new QuackObservable(this);
    }

    @Override
    public void quack() {
        System.out.println("Kwak");
        notifyObservers();
    }

    @Override
    public String toString() {
        return "Duck Call";
    }

    @Override
    public void registerObserver(Observer observer) {
        mObservable.registerObserver(observer);
    }

    @Override
    public void notifyObservers() {
        mObservable.notifyObservers();
    }
}
