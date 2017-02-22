package com.test.dagger.base;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Created by hzshangjiaxiong on 17/1/3.
 */
public class CoffeeMaker {

    private final Lazy<Heater> heater; // Create a possibly costly heater only when we use it.
    private final Pump pump;

    @Inject CoffeeMaker(Lazy<Heater> heater, Thermosiphon pump) {
        this.heater = heater;
        this.pump = pump;
    }

    public void brew() {
        heater.get().on();
        heater.get().isHot();
        pump.pump();
        System.out.println(" [_]P coffee! [_]P ");
        heater.get().off();
    }
}
