package com.test.dagger;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

/**
 * Created by hzshangjiaxiong on 17/1/4.
 */
@Module
public class SubCoffeeModule {

    @Named
    @Provides @IntoSet static String provideSetString() {
        return "e";
    }

    @Provides String provideString() {
        return "subCoffeeModule  ";
    }

    @Provides @Named String provideNamedString() {
        return "subCoffeeComponent";
    }
}
