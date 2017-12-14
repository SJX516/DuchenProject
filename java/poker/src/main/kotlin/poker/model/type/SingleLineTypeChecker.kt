package com.duchen.poker.model.type

import java.util.Arrays

class SingleLineTypeChecker : TypeChecker {

    override val type: CardGroupType
        get() = CardGroupType.SINGLE_LINE

    override val myPossibleCardCount: List<Int>
        get() = Arrays.asList(5, 6, 7, 8, 9, 10, 11, 12)

    override fun getMaxCardIfIsMyType(cards: MutableList<Int>): Int {
        val cardCountArray = IntArray(18)
        for (card in cards) {
            cardCountArray[card]++
        }

        var lineCount = 0
        var i = 3
        while (i < 15) {
            if (cardCountArray[i] == 1) {
                lineCount++
            } else {
                if (lineCount != 0) {
                    break
                }
            }
            i++
        }
        return if (lineCount == cards.size) {
            i - 1
        } else {
            -1
        }
    }
}
