package com.duchen.design.observer;

import com.duchen.design.observer.observer.impl.CurrentConditionsDisplay;
import com.duchen.design.observer.subject.impl.WeatherDataSubject;

public class ObserverTest implements Runnable {

    @Override
    public void run() {
        WeatherDataSubject weatherData = new WeatherDataSubject();
        CurrentConditionsDisplay conditionsDisplay = new CurrentConditionsDisplay(weatherData);
        weatherData.setMeasurements(22, 33);
        weatherData.setMeasurements(42, 23);
        conditionsDisplay.stopUpdate();
        weatherData.setMeasurements(12, 53);
    }
}
