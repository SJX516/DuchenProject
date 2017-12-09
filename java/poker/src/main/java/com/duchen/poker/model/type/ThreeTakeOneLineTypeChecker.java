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
        Integer[] cardNums = (Integer[]) cards.toArray();
        Arrays.sort(cardNums);

        TwoValue<Integer, Integer> threeCard = new TwoValue<>(0, 0);
        int currentCard = 0;
        int lineCount = 0;
        for (int i = 0; i < cardNums.length;) {
            if (lineCount != 0) {
                int leftCard = cardNums.length - i;
                if (leftCard <= 2) {
                    int shouldLineCount = cardNums.length / 4;
                    if (lineCount < shouldLineCount) {
                        return -1;
                    } else {
                        return currentCard;
                    }
                } else {
                    currentCard += 1;
                    //todo 单数全在后面造成进入这里的情况
                    if (cardNums[i] != currentCard || cardNums[i+1] != currentCard || cardNums[i+2] != currentCard) {
                        return -1;
                    } else {
                        lineCount++;
                        i += 3;
                    }
                }
            } else {
                if (threeCard.getKey().intValue() == cardNums[i]) {
                    threeCard.setValue(threeCard.getValue() + 1);
                    if (threeCard.getValue() == 3) {
                        currentCard = threeCard.getKey();
                        lineCount++;
                    }
                } else {
                    threeCard.setKey(cardNums[i]);
                    threeCard.setValue(1);
                }
                i++;
            }
        }
        return -1;
    }
}
