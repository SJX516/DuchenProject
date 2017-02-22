package com.test.dagger;

import java.util.Set;

import javax.inject.Named;

import dagger.Subcomponent;

/**
 * Created by hzshangjiaxiong on 17/1/4.
 */

@Subcomponent(modules = SubCoffeeModule.class)
public interface SubCoffeeComponent {

    @Named String show();

    @Named Set<String> str();

}
