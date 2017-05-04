package com.duchen.design.observer.observer.impl;

import com.duchen.design.observer.observer.DisplayElement;

public class CurrentConditionsDisplay implements com.duchen.design.observer.observer.Observer, DisplayElement {

    private com.duchen.design.observer.subject.Subject mSubject;
    private float mTemp;
    private float mPressure;

    public CurrentConditionsDisplay(com.duchen.design.observer.subject.Subject subject) {
        mSubject = subject;
        mSubject.registerObserver(this);
    }

    public void stopUpdate() {
        mSubject.removeObserver(this);
    }

    @Override
    public void update(float temp, float pressure) {
        mTemp = temp;
        mPressure = pressure;
        display();
    }

    @Override
    public void display() {
        System.out.println("Current conditions: " + mTemp + "F degrees and " + mPressure + " pressure");
    }
}
