package com.duchen.design.compound.observer;

import com.duchen.design.compound.Quackable;

public class Quackologist implements Observer {

    @Override
    public void update(Quackable duck) {
        System.out.println("Quackologist: " + duck + " just quacked.");
    }
}
