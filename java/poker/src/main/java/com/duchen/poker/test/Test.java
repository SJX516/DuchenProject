package com.duchen.poker.test;

import com.duchen.poker.logic.HandCardLogic;

import java.util.ArrayList;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        HandCardLogic logic = new HandCardLogic();
        System.out.println(logic.isOneCardGroup(Arrays.asList(10)));
        System.out.println(logic.isOneCardGroup(Arrays.asList(14, 14)));
        System.out.println(logic.isOneCardGroup(Arrays.asList(12, 12, 12)));
        System.out.println(logic.isOneCardGroup(Arrays.asList(3, 4, 5, 6, 7)));
        System.out.println(logic.isOneCardGroup(Arrays.asList(12, 10, 12, 10, 11, 11)));
        System.out.println(logic.isOneCardGroup(Arrays.asList(3, 3, 3, 4, 4, 4, 6, 6, 6, 5, 5, 5)));
        System.out.println(logic.isOneCardGroup(Arrays.asList(11, 11, 3, 11)));
        System.out.println(logic.isOneCardGroup(Arrays.asList(8, 3, 3, 8, 8)));
        System.out.println(logic.isOneCardGroup(Arrays.asList(3, 3, 3, 2, 4, 4, 2, 4, 5, 6, 5, 8, 5, 6, 8)));
        System.out.println(logic.isOneCardGroup(Arrays.asList(3, 3, 3, 4, 4, 2, 4, 5, 6, 5, 8, 5)));
        System.out.println(logic.isOneCardGroup(Arrays.asList(3, 3, 3, 3)));
        System.out.println(logic.isOneCardGroup(Arrays.asList(3, 3, 3, 5, 3)));
        System.out.println(logic.isOneCardGroup(Arrays.asList(3, 3, 4, 3, 4, 3)));
        System.out.println(logic.isOneCardGroup(Arrays.asList(16, 17)));
        System.out.println(logic.isOneCardGroup(new ArrayList<Integer>()));
        System.out.println(logic.isOneCardGroup(Arrays.asList(12, 12, 11, 11)));
    }

}
