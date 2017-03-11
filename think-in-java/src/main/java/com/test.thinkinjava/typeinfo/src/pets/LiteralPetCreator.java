package com.test.thinkinjava.typeinfo.src.pets;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LiteralPetCreator extends PetCreator {

    public static final List<Class<? extends Pet>> allTypes = Collections.unmodifiableList(
            Arrays.asList( Dog1.class,Dog2.class, Mutt.class, Pug.class, Rat.class, Cat.class));

    private static final List<Class<? extends Pet>> types = allTypes.subList(
            allTypes.indexOf(Mutt.class) , allTypes.size());

    @Override
    public List<Class<? extends Pet>> types() {
        return allTypes;
    }
}
