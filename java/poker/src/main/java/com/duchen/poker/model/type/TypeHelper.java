package com.duchen.poker.model.type;

import com.duchen.poker.model.CardGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypeHelper {

    private static TypeHelper sInstance;

    public static TypeHelper getInstance() {
        if (sInstance == null) {
            sInstance = new TypeHelper();
        }
        return sInstance;
    }

    List<TypeChecker> mCheckers = new ArrayList<>();

    private TypeHelper() {
        mCheckers.add(new ZeroTypeChecker());
        mCheckers.add(new SingleTypeChecker());
        mCheckers.add(new DoubleTypeChecker());
        mCheckers.add(new ThreeTypeChecker());
        mCheckers.add(new SingelLineTypeChecker());
        mCheckers.add(new DoublelLineTypeChecker());
        mCheckers.add(new ThreeLineTypeChecker());
        mCheckers.add(new ThreeTakeOneTypeChecker());
        mCheckers.add(new ThreeTakeTwoTypeChecker());
        mCheckers.add(new ThreeTakeOneLineTypeChecker());
        mCheckers.add(new ThreeTakeTwoLineTypeChecker());
        mCheckers.add(new BombTypeChecker());
        mCheckers.add(new FourTakeOneTypeChecker());
        mCheckers.add(new FourTakeTwoTypeChecker());
        mCheckers.add(new KingTypeChecker());
    }

    public CardGroup isOneCardGroup(List<Integer> cards) {
        int count = cards.size();
        for (TypeChecker checker : mCheckers) {
            if (checker.getMyPossibleCardCount().contains(count)) {
                int maxCard = checker.getMaxCardIfIsMyType(cards);
                if (maxCard >= 0) {
                    CardGroup group = new CardGroup();
                    if (!cards.isEmpty()) {
                        Integer[] cardNums = (Integer[]) cards.toArray();
                        Arrays.sort(cardNums);
                        group.setCardList(Arrays.asList(cardNums));
                    }
                    group.setCardGroupType(checker.getType());
                    group.setMaxCard(maxCard);
                    return group;
                }
            }
        }
        CardGroup error = new CardGroup();
        error.setCardGroupType(CardGroupType.ERROR);
        Integer[] cardNums = (Integer[]) cards.toArray();
        Arrays.sort(cardNums);
        error.setCardList(Arrays.asList(cardNums));
        return error;
    }
}
