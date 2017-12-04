package com.duchen.design.compound.observer;

public interface Observable {
    void registerObserver(Observer observer);
    void notifyObservers();
}
