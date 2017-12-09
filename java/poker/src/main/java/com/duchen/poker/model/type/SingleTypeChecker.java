package com.duchen.poker.model.type;

import java.util.Arrays;
import java.util.List;

public class SingleTypeChecker implements TypeChecker {

    @Override
    public CardGroupType getType() {
        return CardGroupType.SINGLE;
    }

    @Override
    public List<Integer> getMyPossibleCardCount() {
        return Arrays.asList(1);
    }

    @Override
    public int getMaxCardIfIsMyType(List<Integer> cards) {
        return cards.get(0);
    }
}
