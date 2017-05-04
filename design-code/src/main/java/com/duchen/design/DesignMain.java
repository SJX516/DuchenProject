package com.duchen.design;

import com.duchen.design.absFactory.DCPizzaStore;
import com.duchen.design.decorator.beverage.Beverage;
import com.duchen.design.decorator.beverage.impl.Espresso;
import com.duchen.design.decorator.beverage.impl.HouseBlend;
import com.duchen.design.decorator.beverage.condiment.impl.Mocha;
import com.duchen.design.decorator.beverage.condiment.impl.Soy;
import com.duchen.design.decorator.beverage.condiment.impl.Whip;
import com.duchen.design.factoryMethod.pizza.Pizza;
import com.duchen.design.factoryMethod.pizzaStore.PizzaStore;
import com.duchen.design.factoryMethod.pizzaStore.impl.ChicagoPizzaStore;
import com.duchen.design.factoryMethod.pizzaStore.impl.NYPizzaStore;
import com.duchen.design.observer.observer.impl.CurrentConditionsDisplay;
import com.duchen.design.observer.subject.impl.WeatherDataSubject;
import com.duchen.design.strategy.duck.Duck;
import com.duchen.design.strategy.duck.impl.ModelDuck;
import com.duchen.design.strategy.behavior.impl.FlyWithWings;

public class DesignMain {

    enum DesignPattern {
        STRATEGY, OBSERVER, DECORATOR, FACTORY_METHOD, ABS_FACTORY
    }

    public static void main(String[] args) {
        runTestCode(DesignPattern.ABS_FACTORY);
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
            case FACTORY_METHOD:
                PizzaStore nyPizzaStore = new NYPizzaStore();
                PizzaStore chicagoPizzaStore = new ChicagoPizzaStore();
                Pizza pizza = nyPizzaStore.orderPizza("Veggie");
                System.out.println("Ethan ordered a " + pizza.getName() + "\n");
                pizza = chicagoPizzaStore.orderPizza("cheese");
                System.out.println("Joel ordered a " + pizza.getName() + "\n");
                break;
            case ABS_FACTORY:
                PizzaStore dcPizzaStore = new DCPizzaStore();
                pizza = dcPizzaStore.orderPizza("cheese");
                System.out.println("Duchen ordered a " + pizza.getName() + "\n");
                break;
            default:
                break;
        }
    }
}
