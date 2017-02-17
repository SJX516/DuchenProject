package com.test.base;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by hzshangjiaxiong on 17/1/3.
 */
@Singleton
public class ElectricHeater implements Heater {

    boolean heating;

    @Inject
    public ElectricHeater(boolean heating) {
        this.heating = heating;
    }

    @Override public void on() {
        System.out.println("~ ~ ~ heating ~ ~ ~");
        this.heating = true;
    }

    @Override public void off() {
        this.heating = false;
    }

    @Override public boolean isHot() {
        System.out.println(toString());
        return heating;
    }
}
