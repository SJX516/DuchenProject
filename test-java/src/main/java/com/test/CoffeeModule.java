package com.test;

import com.test.base.ElectricHeater;
import com.test.base.Heater;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;
import dagger.multibindings.IntoSet;

/**
 * Created by hzshangjiaxiong on 17/1/3.
 */
@Module(includes = PumpModule.class)
public class CoffeeModule {

    @Provides static Heater providerHeater(ElectricHeater heater) {
        return heater;
    }

    @Provides static boolean providerBoolean() {
        return true;
    }

    @Named
    @Provides @ElementsIntoSet static Set<String> provideSomeStrings() {
        return new HashSet<>(Arrays.asList("a", "b"));
    }

    @Provides @IntoSet static String provideOneString() {
        return "c";
    }

    @Provides static int provideInt() {
        return 33;
    }

    @Provides static String provideSubCoffeeComponent(int code) {
        return new SubCoffeeModule().provideString();
    }

    @Provides static short provideShort() {
        return 3;
    }
}
