package com.test.thinkinjava.typeinfo.src.pets;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 51619 on 2016/3/29 0029.
 */
public abstract class PetCreator {
    private Random rand = new Random(new Date().getTime());

    public abstract List<Class<? extends Pet>> types();

    public Pet randomPet() {
        int n = rand.nextInt(types().size());
        try {
            return types().get(n).newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Pet[] createArray(int size){
        Pet[] result = new Pet[size];
        System.out.println(types());
        for(int i = 0; i < size; i++) {
            result[i] = randomPet();
        }
        return result;
    }

    public ArrayList<Pet> arrayList(int size) {
        ArrayList<Pet> result = new ArrayList<>();
        Collections.addAll(result,createArray(size));
        return  result;
    }
}
