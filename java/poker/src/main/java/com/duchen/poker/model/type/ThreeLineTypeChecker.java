package com.duchen.poker.model.type;

import java.util.Arrays;
import java.util.List;

public class ThreeLineTypeChecker implements TypeChecker {

    @Override
    public CardGroupType getType() {
        return CardGroupType.THREE_LINE;
    }

    @Override
    public List<Integer> getMyPossibleCardCount() {
        return Arrays.asList(6, 9, 12, 15, 18);
    }

    @Override
    public int getMaxCardIfIsMyType(List<Integer> cards) {
        int[] cardCountArray = new int[18];
        for (int card : cards) {
            cardCountArray[card]++;
        }

        int lineCount = 0;
        int i = 3;
        for (; i < 15; i++) {
            if (cardCountArray[i] == 3) {
                lineCount++;
            } else {
                if (lineCount != 0) {
                    break;
                }
            }
        }
        if (lineCount * 3 == cards.size()) {
            return i - 1;
        } else {
            return -1;
        }

    }
}
