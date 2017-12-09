package com.duchen.poker.model.type;

import java.util.Arrays;
import java.util.List;

public class ZeroTypeChecker implements TypeChecker {

    @Override
    public CardGroupType getType() {
        return CardGroupType.ZERO;
    }

    @Override
    public List<Integer> getMyPossibleCardCount() {
        return Arrays.asList(0);
    }

    @Override
    public int getMaxCardIfIsMyType(List<Integer> cards) {
        return 0;
    }

}
