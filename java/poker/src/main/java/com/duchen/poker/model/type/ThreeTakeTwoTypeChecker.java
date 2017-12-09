package com.duchen.poker.model.type;

import com.duchen.poker.model.TwoValue;

import java.util.Arrays;
import java.util.List;

public class ThreeTakeTwoTypeChecker implements TypeChecker {

    @Override
    public CardGroupType getType() {
        return CardGroupType.THREE_TAKE_TWO;
    }

    @Override
    public List<Integer> getMyPossibleCardCount() {
        return Arrays.asList(5);
    }

    @Override
    public int getMaxCardIfIsMyType(List<Integer> cards) {
        TwoValue<Integer, Integer> cardCount1 = new TwoValue<>(0, 0);
        TwoValue<Integer, Integer> cardCount2 = new TwoValue<>(0, 0);

        for (int card : cards) {
            if (cardCount1.getKey() == 0) {
                cardCount1.setKey(card);
                cardCount1.setValue(1);
            } else if (cardCount1.getKey() == card) {
                cardCount1.setValue(cardCount1.getValue() + 1);
            } else if (cardCount2.getKey() == 0) {
                cardCount2.setKey(card);
                cardCount2.setValue(1);
            } else if (cardCount2.getKey() == card) {
                cardCount2.setValue(cardCount2.getValue() + 1);
            } else {
                return -1;
            }
        }

        if (cardCount1.getValue() == 3) {
            return cardCount1.getKey();
        } else if (cardCount2.getValue() == 3) {
            return cardCount2.getKey();
        } else {
            return -1;
        }
    }

}
