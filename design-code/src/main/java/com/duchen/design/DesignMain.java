package com.duchen.design;

import com.duchen.design.observer.CurrentConditionsDisplay;
import com.duchen.design.observer.WeatherDataSubject;
import com.duchen.design.strategy.Duck;
import com.duchen.design.strategy.ModelDuck;
import com.duchen.design.strategy.behavior.FlyWithWings;

public class DesignMain {

    enum DesignPattern {
        STRATEGY, OBSERVER
    }

    public static void main(String[] args) {
        runTestCode(DesignPattern.OBSERVER);
    }

    private static void runTestCode(DesignPattern pattern) {
        switch (pattern) {
            case STRATEGY:
                Duck duck = new ModelDuck();
                duck.display();
                duck.performQuack();
                duck.performFly();
                duck.setFlyBehavior(new FlyWithWings());
                duck.performFly();
                break;
            case OBSERVER:
                WeatherDataSubject weatherData = new WeatherDataSubject();
                CurrentConditionsDisplay conditionsDisplay = new CurrentConditionsDisplay(weatherData);
                weatherData.setMeasurements(22, 33);
                weatherData.setMeasurements(42, 23);
                conditionsDisplay.stopUpdate();
                weatherData.setMeasurements(12, 53);
                break;
            default:
                break;
        }
    }
}
