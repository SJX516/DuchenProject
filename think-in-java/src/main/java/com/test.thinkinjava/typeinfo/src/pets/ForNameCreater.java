package com.test.thinkinjava.typeinfo.src.pets;

import java.util.ArrayList;
import java.util.List;

public class ForNameCreater extends PetCreator {

    private static List<Class<? extends Pet>> types = new ArrayList<>();

    private static String[] typeNames = {"pets.Dog", "pets.Cat", "pets.Mutt", "pets.Pug", "pets.Rat"};

    @SuppressWarnings("unchecked")
    private static void loader() {
        for (String name : typeNames) {
            try {
                types.add((Class<? extends Pet>) Class.forName(name));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static {
        loader();
    }

    @Override
    public List<Class<? extends Pet>> types() {
        return types;
    }
}
