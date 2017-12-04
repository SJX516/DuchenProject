package com.duchen.design.observer.subject.impl;

import com.duchen.design.observer.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataSubject implements com.duchen.design.observer.subject.Subject {

    private float mTemp;
    private float mPressure;
    private List<Observer> mObservers = new ArrayList<>();

    @Override
    public void registerObserver(Observer observer) {
        mObservers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        mObservers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : mObservers) {
            o.update(mTemp, mPressure);
        }
    }

    public void measurementsChanged() {
        notifyObservers();
    }

    public void setMeasurements(float temp, float pressure) {
        this.mTemp = temp;
        this.mPressure = pressure;
        measurementsChanged();
    }
}
