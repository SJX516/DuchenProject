package com.test.thinkinjava.typeinfo.src.pets;

import java.util.HashMap;

/**
 * Created by 51619 on 2016/3/29 0029.
 */

public class PetCount {
    static class PetCounter extends HashMap<String, Integer> {
        public void count(String type) {
            Integer quantity = get(type);
            if (quantity == null) {
                put(type, 1);
            } else {
                put(type, quantity + 1);
            }
        }
    }


    public static void countPets(PetCreator creator) {
        PetCounter counter = new PetCounter();
        for (Pet pet : creator.createArray(10)) {
            System.out.print(pet.getClass().getSimpleName() + " ");
            counter.count(pet.getClass().getSimpleName());

//            if (pet instanceof Pet) {
//                counter.count("Pet");
//            }
//            if (pet instanceof Dog) {
//                counter.count("Dog");
//            }
//            if (pet instanceof Cat) {
//                counter.count("Cat");
//            }
//            if (pet instanceof Mutt) {
//                counter.count("Mutt");
//            }
//            if (pet instanceof Pug) {
//                counter.count("Pug");
//            }
//            if (pet instanceof Rat) {
//                counter.count("Rat");
//            }
        }
        System.out.println();
        System.out.print(counter);
    }

    public static void main(String[] args) {
//        countPets(new LiteralPetCreator());

        TypeCounter counter = new TypeCounter(Pet.class);

        for(Pet pet : new LiteralPetCreator().createArray(10) ){
            counter.count(pet);
        }
        System.out.println(counter);
    }
}
