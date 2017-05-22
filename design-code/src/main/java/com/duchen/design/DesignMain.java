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
import com.duchen.design.composite.MenuComponent.MenuComponent;
import com.duchen.design.composite.MenuComponent.MenuGroup;
import com.duchen.design.composite.MenuComponent.MenuItem;
import com.duchen.design.composite.WaitressComposite;
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
import com.duchen.design.iterator.Waitress;
import com.duchen.design.iterator.menu.DinerMenu;
import com.duchen.design.iterator.menu.Menu;
import com.duchen.design.iterator.menu.PancakeHouseMenu;
import com.duchen.design.observer.observer.impl.CurrentConditionsDisplay;
import com.duchen.design.observer.subject.impl.WeatherDataSubject;
import com.duchen.design.state.GumballMachine;
import com.duchen.design.strategy.behavior.impl.FlyWithWings;
import com.duchen.design.strategy.duck.Duck;
import com.duchen.design.strategy.duck.impl.ModelDuck;
import com.duchen.design.templateMethod.impl.Coffee;
import com.duchen.design.templateMethod.impl.CoffeeWithHook;
import com.duchen.design.templateMethod.impl.Tea;

public class DesignMain {

    enum DesignPattern {
        STRATEGY, OBSERVER, DECORATOR, FACTORY_METHOD, ABS_FACTORY, SINGLETON, COMMAND, ADAPTER, FACADE,
        TEMPLATE_METHOD, ITERATOR, COMPOSITE, STATE
    }

    public static void main(String[] args) {
        runTestCode(DesignPattern.STATE);
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
            case ITERATOR:
                Menu breakfastMenu = new PancakeHouseMenu();
                Menu launchMenu = new DinerMenu();
                Waitress waitress = new Waitress(breakfastMenu, launchMenu);
                waitress.printMenu();
                break;
            case COMPOSITE:
                MenuComponent pancakeHouseMenu = new MenuGroup("PANCAKE HOUSE MENU", "Breakfast");
                MenuComponent dinerMenu = new MenuGroup("DINER MENU", "Launch");
                MenuComponent cafeMenu = new MenuGroup("CAFE MENU", "Dinner");
                MenuComponent dessertMenu = new MenuGroup("DESSERT MENU", "Dessert of course!");

                pancakeHouseMenu.add(new MenuItem("K&B's Pancake Breakfast", "Pancake with fried eggs, toast", true, 2.99));
                pancakeHouseMenu.add(new MenuItem("Regular Pancake Breakfast", "Pancake with fried eggs, sausage", false, 2.99));
                pancakeHouseMenu.add(new MenuItem("Blueberry Breakfast", "Pancake made with fresh blueberries", true, 3.49));
                pancakeHouseMenu.add(new MenuItem("Waffles", "Waffles, with your choice of blueberries or strawberries", true, 3.59));

                dinerMenu.add(new MenuItem("Vegetarian BLT", "Bacon with lettuce & tomato on whole wheat", true, 2.99));
                dinerMenu.add(new MenuItem("BLT", "Bacon with lettuce & tomato on whole wheat", false, 2.99));
                dinerMenu.add(new MenuItem("Soup of the day", "with a side of potato salad", false, 3.29));
                dinerMenu.add(new MenuItem("Hotdog", "A hot dog, with relish, onions, topped with cheese", false, 3.05));

                cafeMenu.add(new MenuItem("Veggie Burger and Air Fries", "Veggie burger on a whole wheat bun, lettuce", true, 3.99));
                cafeMenu.add(new MenuItem("Soup of the day", "A cup of the soup of the day, with a side salad", false, 3.69));
                cafeMenu.add(new MenuItem("Burrito", "A large burrito, with whole pinto beans, salsa, guacamole", true, 4.29));

                dessertMenu.add(new MenuItem("Apple pie", "Apple pie with flaky crust, topped with vanilla ice cream", true, 1.59));

                MenuComponent allMenu = new MenuGroup("ALL MENUS", "All menus combined");
                allMenu.add(pancakeHouseMenu);
                allMenu.add(dinerMenu);
                allMenu.add(cafeMenu);

                dinerMenu.add(dessertMenu);

                WaitressComposite waitressComposite = new WaitressComposite(allMenu);
                waitressComposite.printVegetarianMenu();

                break;
            case STATE:
                GumballMachine gumballMachine = new GumballMachine(1);
                System.out.println(gumballMachine);
                gumballMachine.insertQuarter();
                gumballMachine.turnCrank();
                System.out.println(gumballMachine);
                gumballMachine.insertQuarter();
                gumballMachine.turnCrank();
                gumballMachine.refill(2);
                gumballMachine.insertQuarter();
                gumballMachine.turnCrank();
                System.out.println(gumballMachine);
                break;
            default:
                break;
        }
    }
}
