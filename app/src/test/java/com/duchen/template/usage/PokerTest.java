package com.duchen.template.usage;


import com.duchen.template.usage.Poker.PrintUtil;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import poker.CardLibrary;
import poker.Poker;
import poker.logic.HandCardLogic;
import poker.model.CardGroup;


/**
 * To work on unit tests, switch the TestKt Artifact in the Build Variants view.
 */
public class PokerTest {

    @Test
    public void testPoker() throws Exception {
        Poker poker = new Poker();
        poker.startNewGame();
    }


    @Test
    public void testGetCardGroup() {
        HandCardLogic logic = new HandCardLogic();
        testGetCard(logic, "4567899XXJJQQKA2I", 4);
        testGetCard(logic, "334555678899XJQKAA22", 6);
        testGetCard(logic, "3455667789XJJKKAAI", 6);
        testGetCard(logic, "34566677789XJJKKAAI", 8);

        testGetCard(logic, "334466778XJQKKA2L", 9);
        testGetCard(logic, "3344556789XQKKA2L", 7);
        testGetCard(logic, "34567899XXJJKKAAI", 5);
    }

    void testGetCard(HandCardLogic logic, String cards, int exceptRound) {
        List<CardGroup> groups = logic.getCardGroupList(CardLibrary.Companion.getCardList(cards));
        if (exceptRound == groups.size()) {
            System.out.println("||--  " + "SUCC" + "  ----  EXCEPT ROUND " + exceptRound);
        } else {
            System.err.println("||--  " + "ERROR" + "  ----  EXCEPT ROUND " + exceptRound + "  " + cards);
        }
        PrintUtil.Companion.printCardGroups(CardLibrary.Companion.getCardList(cards), groups);
    }

    @Test
    public void testCheckGroup() {
        HandCardLogic logic = new HandCardLogic();
        System.out.println(logic.getOneCardGroup(Arrays.asList(10)));
        System.out.println(logic.getOneCardGroup(Arrays.asList(14, 14)));
        System.out.println(logic.getOneCardGroup(Arrays.asList(12, 12, 12)));
        System.out.println(logic.getOneCardGroup(Arrays.asList(3, 4, 5, 6, 7)));
        System.out.println(logic.getOneCardGroup(Arrays.asList(12, 10, 12, 10, 11, 11)));
        System.out.println(logic.getOneCardGroup(Arrays.asList(3, 3, 3, 4, 4, 4, 6, 6, 6, 5, 5, 5)));
        System.out.println(logic.getOneCardGroup(Arrays.asList(11, 11, 3, 11)));
        System.out.println(logic.getOneCardGroup(Arrays.asList(8, 3, 3, 8, 8)));
        System.out.println(logic.getOneCardGroup(Arrays.asList(3, 3, 3, 11, 4, 4, 11, 4, 5, 6, 5, 8, 5, 6, 8)));
        System.out.println(logic.getOneCardGroup(Arrays.asList(3, 3, 3, 4, 4, 9, 4, 5, 6, 5, 8, 5)));
        System.out.println(logic.getOneCardGroup(Arrays.asList(3, 3, 3, 3)));
        System.out.println(logic.getOneCardGroup(Arrays.asList(3, 3, 3, 5, 3)));
        System.out.println(logic.getOneCardGroup(Arrays.asList(3, 3, 4, 3, 4, 3)));
        System.out.println(logic.getOneCardGroup(Arrays.asList(16, 17)));
        System.out.println(logic.getOneCardGroup(new ArrayList<Integer>()));
        System.out.println(logic.getOneCardGroup(Arrays.asList(12, 12, 11, 11)));
    }
}