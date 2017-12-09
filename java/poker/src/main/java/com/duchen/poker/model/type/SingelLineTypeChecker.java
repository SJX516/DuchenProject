package com.duchen.poker.model.type;

import java.util.Arrays;
import java.util.List;

public class SingelLineTypeChecker implements TypeChecker {

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
        Integer[] cardNums = (Integer[]) cards.toArray();
        Arrays.sort(cardNums);
        int currentNum = 0;
        for (int i = 0; i < cardNums.length; i++) {
            if (i == 0) {
                currentNum = cardNums[i];
            } else {
                if (cardNums[i] != currentNum + 1) {
                    return -1;
                } else {
                    currentNum = cardNums[i];
                }
            }
        }
        if (currentNum > 14) {
            return -1;
        }
        return currentNum;
    }
}
