package com.test.dagger.base;

import com.test.dagger.CoffeeApp;

import javax.inject.Inject;

/**
 * Created by hzshangjiaxiong on 17/1/3.
 */
public class TestPump implements Pump {

//    private final Heater heater;
    @Inject TestData mData;

    public TestPump() {
        CoffeeApp.coffee.inject(this);
    }

    @Override
    public void pump() {
//        heater.get().isHot();
        System.out.println("=> => testing pumping => => " + mData.getData());
    }
}
