package com.duchen.design.observer;

public class CurrentConditionsDisplay implements Observer, DisplayElement {

    private Subject mSubject;
    private float mTemp;
    private float mPressure;

    public CurrentConditionsDisplay(Subject subject) {
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
