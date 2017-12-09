package com.duchen.poker.model.type;

import java.util.Arrays;
import java.util.List;

public class BombTypeChecker implements TypeChecker {

    @Override
    public CardGroupType getType() {
        return CardGroupType.BOMB_CARD;
    }

    @Override
    public List<Integer> getMyPossibleCardCount() {
        return Arrays.asList(4);
    }

    @Override
    public int getMaxCardIfIsMyType(List<Integer> cards) {
        int card = cards.get(0);
        for (int data : cards) {
            if (data != card) {
                return -1;
            }
        }
        return card;
    }
}
