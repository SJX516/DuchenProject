package com.duchen.poker.model.type;

import java.util.Arrays;
import java.util.List;

public class DoubleTypeChecker implements TypeChecker {

    @Override
    public CardGroupType getType() {
        return CardGroupType.DOUBLE;
    }

    @Override
    public List<Integer> getMyPossibleCardCount() {
        return Arrays.asList(2);
    }

    @Override
    public int getMaxCardIfIsMyType(List<Integer> cards) {
        if (cards.get(0).intValue() == cards.get(1).intValue()) {
            return cards.get(0);
        }
        return -1;
    }

}
