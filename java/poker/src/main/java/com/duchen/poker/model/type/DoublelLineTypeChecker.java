package com.duchen.poker.model.type;

import java.util.Arrays;
import java.util.List;

public class DoublelLineTypeChecker implements TypeChecker {

    @Override
    public CardGroupType getType() {
        return CardGroupType.DOUBLE_LINE;
    }

    @Override
    public List<Integer> getMyPossibleCardCount() {
        return Arrays.asList(6, 8, 10, 12, 14, 16, 18, 20);
    }

    @Override
    public int getMaxCardIfIsMyType(List<Integer> cards) {
        Integer[] cardNums = (Integer[]) cards.toArray();
        Arrays.sort(cardNums);
        int currentCard = 0;
        for (int i = 0; i < cardNums.length; i++) {
            if (i % 2 == 0) {
                int newCard = cardNums[i];
                if (currentCard == 0) {
                    currentCard = newCard;
                } else {
                    if (newCard != currentCard + 1) {
                        return -1;
                    } else {
                        currentCard = newCard;
                    }
                }
            } else {
                if (cardNums[i] != currentCard) {
                    return -1;
                }
            }
        }
        return currentCard;
    }
}
