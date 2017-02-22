package com.test.dagger;

import com.test.dagger.base.TestPump;

/**
 * Created by hzshangjiaxiong on 17/1/3.
 */
public class CoffeeApp {

    public static CoffeeComponent coffee;

    public static void main(String[] args) {
        coffee = DaggerCoffeeComponent.builder().build();
        coffee.maker().brew();

        System.out.println(coffee.strings());
//        System.out.println(coffee.subCoffeeString());
        System.out.println(coffee.subCoffeeComponent().show());
        System.out.println(coffee.subCoffeeComponent().str());

        new TestPump().pump();
    }
}
