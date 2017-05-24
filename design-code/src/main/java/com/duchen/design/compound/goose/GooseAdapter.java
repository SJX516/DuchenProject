package com.duchen.design.compound.goose;

import com.duchen.design.compound.Quackable;
import com.duchen.design.compound.observer.Observer;
import com.duchen.design.compound.observer.QuackObservable;

public class GooseAdapter implements Quackable {

    private QuackObservable mObservable;
    private Goose mGoose;

    public GooseAdapter(Goose goose) {
        mGoose = goose;
        mObservable = new QuackObservable(this);
    }

    @Override
    public void quack() {
        mGoose.honk();
        notifyObservers();
    }

    @Override
    public String toString() {
        return mGoose.toString();
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
