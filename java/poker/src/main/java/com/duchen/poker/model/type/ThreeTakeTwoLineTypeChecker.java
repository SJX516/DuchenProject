package com.duchen.poker.model.type;

import com.duchen.poker.model.TwoValue;

import java.util.Arrays;
import java.util.List;

public class ThreeTakeTwoLineTypeChecker implements TypeChecker {

    @Override
    public CardGroupType getType() {
        return CardGroupType.THREE_TAKE_TWO_LINE;
    }

    @Override
    public List<Integer> getMyPossibleCardCount() {
        return Arrays.asList(10, 15, 20);
    }

    @Override
    public int getMaxCardIfIsMyType(List<Integer> cards) {
        int[] cardCountArray = new int[18];
        for (int card : cards) {
            cardCountArray[card]++;
        }

        int lineCount = 0;
        int doubleCount = 0;
        int i = 3, j = 3;
        for (; i < 15; i++) {
            if (cardCountArray[i] == 3) {
                lineCount++;
            } else {
                if (lineCount != 0) {
                    break;
                }
            }
        }
        for (; j < 16; j++) {
            if (cardCountArray[j] == 2 || cardCountArray[j] == 4) {
                doubleCount += cardCountArray[i] / 2;
            }
        }
        if (lineCount == doubleCount && lineCount * 5 == cards.size()) {
            return i - 1;
        } else {
            return -1;
        }
    }
}
