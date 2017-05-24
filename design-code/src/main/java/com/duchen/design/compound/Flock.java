package com.duchen.design.compound;

import com.duchen.design.compound.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class Flock implements Quackable {

    private List<Quackable> mQuacks = new ArrayList<>();

    public void add(Quackable quackable) {
        mQuacks.add(quackable);
    }

    @Override
    public void registerObserver(Observer observer) {
        for (Quackable quackable : mQuacks) {
            quackable.registerObserver(observer);
        }
    }

    @Override
    public void notifyObservers() {
        for (Quackable quackable : mQuacks) {
            quackable.notifyObservers();
        }
    }

    @Override
    public void quack() {
        for (Quackable quackable : mQuacks) {
            quackable.quack();
        }
    }
}
