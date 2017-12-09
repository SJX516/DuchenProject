package com.duchen.poker.model.type;

import java.util.List;

public interface TypeChecker {

    CardGroupType getType();

    List<Integer> getMyPossibleCardCount();

    int getMaxCardIfIsMyType(List<Integer> cards);

}
