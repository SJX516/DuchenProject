package com.test.thinkinjava.typeinfo.src.pets;

/**
 * Created by 51619 on 2016/3/29 0029.
 */
public class Pet {
    private String name = getClass().getSimpleName();

    public Pet(String name) {
        this.name = name;
    }

    public Pet() {

    }
}
