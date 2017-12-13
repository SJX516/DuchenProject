package com.duchen.poker.model.type;

import com.duchen.poker.model.TwoValue;

import java.util.Arrays;
import java.util.List;

public class ThreeTakeOneLineTypeChecker implements TypeChecker {

    @Override
    public CardGroupType getType() {
        return CardGroupType.THREE_TAKE_ONE_LINE;
    }

    @Override
    public List<Integer> getMyPossibleCardCount() {
        return Arrays.asList(8, 12, 16, 20);
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
        if (lineCount * 4 == cards.size()) {
            return i - 1;
        } else {
            return -1;
        }
    }
}
