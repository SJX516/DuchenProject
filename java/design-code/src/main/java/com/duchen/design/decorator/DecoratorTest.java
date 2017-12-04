package com.duchen.design.decorator;

import com.duchen.design.decorator.beverage.Beverage;
import com.duchen.design.decorator.beverage.condiment.impl.Mocha;
import com.duchen.design.decorator.beverage.condiment.impl.Soy;
import com.duchen.design.decorator.beverage.condiment.impl.Whip;
import com.duchen.design.decorator.beverage.impl.Espresso;
import com.duchen.design.decorator.beverage.impl.HouseBlend;

public class DecoratorTest implements Runnable {
    @Override
    public void run() {
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
    }
}
