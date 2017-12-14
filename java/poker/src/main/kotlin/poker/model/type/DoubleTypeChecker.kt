package com.duchen.poker.model.type

import java.util.Arrays

class DoubleTypeChecker : TypeChecker {

    override val type: CardGroupType
        get() = CardGroupType.DOUBLE

    override val myPossibleCardCount: List<Int>
        get() = Arrays.asList(2)

    override fun getMaxCardIfIsMyType(cards: MutableList<Int>): Int {
        return if (cards[0] == cards[1]) {
            cards[0]
        } else -1
    }

}
