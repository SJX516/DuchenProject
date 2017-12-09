package com.duchen.poker.model.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ErrorTypeChecker implements TypeChecker {

    @Override
    public CardGroupType getType() {
        return CardGroupType.ERROR;
    }

    @Override
    public List<Integer> getMyPossibleCardCount() {
        return Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
    }

    @Override
    public int getMaxCardIfIsMyType(List<Integer> cards) {
        return -1;
    }
}
