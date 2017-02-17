package com.test.base;

import javax.inject.Inject;

/**
 * Created by hzshangjiaxiong on 17/1/3.
 */
public class Thermosiphon implements Pump {

//    private final Heater heater;
    @Inject short field;

    @Inject
    Thermosiphon() {
//        this.heater = heater;
    }

//    public Thermosiphon() {
//        CoffeeApp.coffee.inject(this);
//    }

    @Override
    public void pump() {
//        heater.get().isHot();
        System.out.println("=> => pumping => => " + field);
    }
}
