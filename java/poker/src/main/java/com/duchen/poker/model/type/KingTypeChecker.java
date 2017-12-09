package com.duchen.poker.model.type;

import java.util.Arrays;
import java.util.List;

public class KingTypeChecker implements TypeChecker {

    @Override
    public CardGroupType getType() {
        return CardGroupType.KING_CARD;
    }

    @Override
    public List<Integer> getMyPossibleCardCount() {
        return Arrays.asList(2);
    }

    @Override
    public int getMaxCardIfIsMyType(List<Integer> cards) {
        if (cards.get(0) >= 16 && cards.get(1) >= 16) {
            return 17;
        } else {
            return -1;
        }
    }
}
