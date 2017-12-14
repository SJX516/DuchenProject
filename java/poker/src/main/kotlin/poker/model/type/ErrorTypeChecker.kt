package com.duchen.poker.model.type

import java.util.Arrays

class ErrorTypeChecker : TypeChecker {

    override val type: CardGroupType
        get() = CardGroupType.ERROR

    override val myPossibleCardCount: List<Int>
        get() = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)

    override fun getMaxCardIfIsMyType(cards: MutableList<Int>): Int {
        return -1
    }
}
