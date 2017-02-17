package com.test;

import com.test.base.CoffeeMaker;
import com.test.base.TestPump;

import java.util.Set;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = CoffeeModule.class)
public interface CoffeeComponent {

    void inject(TestPump thermosiphon);

    CoffeeMaker maker();

    @Named Set<String> strings();

//    String subCoffeeString();

    SubCoffeeComponent subCoffeeComponent();

}
