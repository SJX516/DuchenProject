package com.duchen.design.compound.observer;

import com.duchen.design.compound.Quackable;

import java.util.ArrayList;
import java.util.List;

public class QuackObservable implements Observable {

    private List<Observer> mObservers = new ArrayList<>();
    private Quackable mQuackable;

    public QuackObservable(Quackable quackable) {
        mQuackable = quackable;
    }

    @Override
    public void registerObserver(Observer observer) {
        mObservers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : mObservers) {
            observer.update(mQuackable);
        }
    }
}
