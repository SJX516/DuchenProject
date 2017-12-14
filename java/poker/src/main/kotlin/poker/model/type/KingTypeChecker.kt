package com.duchen.poker.model.type

import java.util.Arrays

class KingTypeChecker : TypeChecker {

    override val type: CardGroupType
        get() = CardGroupType.KING_CARD

    override val myPossibleCardCount: List<Int>
        get() = Arrays.asList(2)

    override fun getMaxCardIfIsMyType(cards: MutableList<Int>): Int {
        return if (cards[0] >= 16 && cards[1] >= 16) {
            17
        } else {
            -1
        }
    }
}
