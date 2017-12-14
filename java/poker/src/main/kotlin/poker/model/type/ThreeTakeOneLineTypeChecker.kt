package com.duchen.poker.model.type

import com.duchen.poker.model.TwoValue

import java.util.Arrays

class ThreeTakeOneLineTypeChecker : TypeChecker {

    override val type: CardGroupType
        get() = CardGroupType.THREE_TAKE_ONE_LINE

    override val myPossibleCardCount: List<Int>
        get() = Arrays.asList(8, 12, 16, 20)

    override fun getMaxCardIfIsMyType(cards: MutableList<Int>): Int {
        val cardCountArray = IntArray(18)
        for (card in cards) {
            cardCountArray[card]++
        }

        var lineCount = 0
        var i = 3
        while (i < 15) {
            if (cardCountArray[i] == 3) {
                lineCount++
            } else {
                if (lineCount != 0) {
                    break
                }
            }
            i++
        }
        return if (lineCount * 4 == cards.size) {
            i - 1
        } else {
            -1
        }
    }
}
