package com.duchen.design;

import com.duchen.design.absFactory.DCPizzaStore;
import com.duchen.design.adapter.TurkeyAdapter;
import com.duchen.design.adapter.duck.SimpleDuck;
import com.duchen.design.adapter.turkey.impl.WildTurkey;
import com.duchen.design.command.RemoteControl;
import com.duchen.design.command.command.Command;
import com.duchen.design.command.command.impl.CeilingFanHighCommand;
import com.duchen.design.command.command.impl.CeilingFanLowCommand;
import com.duchen.design.command.command.impl.LightOffCommand;
import com.duchen.design.command.command.impl.LightOnCommand;
import com.duchen.design.command.command.impl.ListCommand;
import com.duchen.design.command.receiver.CeilingFan;
import com.duchen.design.command.receiver.Light;
import com.duchen.design.decorator.beverage.Beverage;
import com.duchen.design.decorator.beverage.condiment.impl.Mocha;
import com.duchen.design.decorator.beverage.condiment.impl.Soy;
import com.duchen.design.decorator.beverage.condiment.impl.Whip;
import com.duchen.design.decorator.beverage.impl.Espresso;
import com.duchen.design.decorator.beverage.impl.HouseBlend;
import com.duchen.design.facade.HomeTheaterFacade;
import com.duchen.design.factoryMethod.pizza.Pizza;
import com.duchen.design.factoryMethod.pizzaStore.PizzaStore;
import com.duchen.design.factoryMethod.pizzaStore.impl.ChicagoPizzaStore;
import com.duchen.design.factoryMethod.pizzaStore.impl.NYPizzaStore;
import com.duchen.design.observer.observer.impl.CurrentConditionsDisplay;
import com.duchen.design.observer.subject.impl.WeatherDataSubject;
import com.duchen.design.strategy.behavior.impl.FlyWithWings;
import com.duchen.design.strategy.duck.Duck;
import com.duchen.design.strategy.duck.impl.ModelDuck;
import com.duchen.design.templateMethod.impl.Coffee;
import com.duchen.design.templateMethod.impl.CoffeeWithHook;
import com.duchen.design.templateMethod.impl.Tea;

public class DesignMain {

    enum DesignPattern {
        STRATEGY, OBSERVER, DECORATOR, FACTORY_METHOD, ABS_FACTORY, SINGLETON, COMMAND, ADAPTER, FACADE, TEMPLATE_METHOD
    }

    public static void main(String[] args) {
        runTestCode(DesignPattern.TEMPLATE_METHOD);
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
            case COMMAND:
                RemoteControl remoteControl = new RemoteControl();
                Light light = new Light();
                LightOnCommand lightOnCommand = new LightOnCommand(light);
                LightOffCommand lightOffCommand = new LightOffCommand(light);
                remoteControl.setCommand(0, lightOnCommand, lightOffCommand);

                CeilingFan fan = new CeilingFan();
                CeilingFanHighCommand highCommand = new CeilingFanHighCommand(fan);
                CeilingFanLowCommand lowCommand = new CeilingFanLowCommand(fan);
                remoteControl.setCommand(1, highCommand, lowCommand);
                System.out.println(remoteControl);

                Command[] onCommands = {lightOnCommand, highCommand};
                Command[] offCommands = {lightOffCommand, lowCommand};
                ListCommand openAllCommand = new ListCommand(onCommands);
                ListCommand closeAllCommand = new ListCommand(offCommands);

                remoteControl.setCommand(2, openAllCommand, closeAllCommand);

                remoteControl.onButtonPushed(2);
                remoteControl.offButtonPushed(2);
                remoteControl.undoButtonPushed();

                remoteControl.onButtonPushed(0);
                remoteControl.offButtonPushed(0);
                remoteControl.undoButtonPushed();
                break;
            case ADAPTER:
                WildTurkey wildTurkey = new WildTurkey();
                SimpleDuck turkeyAdapter = new TurkeyAdapter(wildTurkey);
                System.out.println("\nThe Turkey says...");
                wildTurkey.gobble();
                wildTurkey.fly();
                System.out.println("\nThe Duck says...");
                turkeyAdapter.quack();
                turkeyAdapter.fly();
                break;
            case FACADE:
                HomeTheaterFacade homeTheaterFacade = new HomeTheaterFacade();
                homeTheaterFacade.watchMovie("Star Wars");
                homeTheaterFacade.endMovie();
                break;
            case TEMPLATE_METHOD:
                Tea tea = new Tea();
                System.out.println("\nMaking tea...");
                tea.prepareRecipe();
                Coffee coffee = new Coffee();
                System.out.println("\nMaking coffee...");
                coffee.prepareRecipe();
                System.out.println("\nMaking coffee with hook...");
                Coffee coffeeWithHook = new CoffeeWithHook();
                coffeeWithHook.prepareRecipe();
                break;
            default:
                break;
        }
    }
}
