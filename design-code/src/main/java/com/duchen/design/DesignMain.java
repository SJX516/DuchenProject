package com.duchen.design;

import com.duchen.design.decorator.beverage.Beverage;
import com.duchen.design.decorator.beverage.Espresso;
import com.duchen.design.decorator.beverage.HouseBlend;
import com.duchen.design.decorator.condiment.Mocha;
import com.duchen.design.decorator.condiment.Soy;
import com.duchen.design.decorator.condiment.Whip;
import com.duchen.design.observer.CurrentConditionsDisplay;
import com.duchen.design.observer.WeatherDataSubject;
import com.duchen.design.strategy.Duck;
import com.duchen.design.strategy.ModelDuck;
import com.duchen.design.strategy.behavior.FlyWithWings;

public class DesignMain {

    enum DesignPattern {
        STRATEGY, OBSERVER, DECORATOR
    }

    public static void main(String[] args) {
        runTestCode(DesignPattern.DECORATOR);
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
            case DECORATOR:
                Beverage beverage = new Espresso();
                System.out.println(beverage.getDescription() + " $" + beverage.cost());
                Beverage beverage2 = new HouseBlend();
                beverage2 = new Mocha(beverage2);
                beverage2 = new Mocha(beverage2);
                beverage2 = new Whip(beverage2);
                System.out.println(beverage2.getDescription() + " $" + beverage2.cost());
                Beverage beverage3 = new Espresso();
                beverage3 = new Soy(beverage3);
                beverage3 = new Mocha(beverage3);
                beverage3 = new Whip(beverage3);
                System.out.println(beverage3.getDescription() + " $" + beverage3.cost());
                break;
            default:
                break;
        }
    }
}
