package com.duchen.poker.model.type;

import java.util.Arrays;
import java.util.List;

public class SingleLineTypeChecker implements TypeChecker {

    @Override
    public CardGroupType getType() {
        return CardGroupType.SINGLE_LINE;
    }

    @Override
    public List<Integer> getMyPossibleCardCount() {
        return Arrays.asList(5, 6, 7, 8, 9, 10, 11, 12);
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
            if (cardCountArray[i] == 1) {
                lineCount++;
            } else {
                if (lineCount != 0) {
                    break;
                }
            }
        }
        if (lineCount == cards.size()) {
            return i - 1;
        } else {
            return -1;
        }
    }
}
