package com.duchen.poker.model.type;

import java.util.Arrays;
import java.util.List;

public class ThreeTypeChecker implements TypeChecker {

    @Override
    public CardGroupType getType() {
        return CardGroupType.THREE;
    }

    @Override
    public List<Integer> getMyPossibleCardCount() {
        return Arrays.asList(3);
    }

    @Override
    public int getMaxCardIfIsMyType(List<Integer> cards) {
        if (cards.get(0).intValue() == cards.get(1) && cards.get(1).intValue() == cards.get(2)) {
            return cards.get(0);
        }
        return -1;
    }
}
