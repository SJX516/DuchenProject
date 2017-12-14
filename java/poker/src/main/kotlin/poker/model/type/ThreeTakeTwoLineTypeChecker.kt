package com.duchen.poker.model.type

import com.duchen.poker.model.TwoValue

import java.util.Arrays

class ThreeTakeTwoLineTypeChecker : TypeChecker {

    override val type: CardGroupType
        get() = CardGroupType.THREE_TAKE_TWO_LINE

    override val myPossibleCardCount: List<Int>
        get() = Arrays.asList(10, 15, 20)

    override fun getMaxCardIfIsMyType(cards: MutableList<Int>): Int {
        val cardCountArray = IntArray(18)
        for (card in cards) {
            cardCountArray[card]++
        }

        var lineCount = 0
        var doubleCount = 0
        var i = 3
        var j = 3
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
        while (j < 16) {
            if (cardCountArray[j] == 2 || cardCountArray[j] == 4) {
                doubleCount += cardCountArray[i] / 2
            }
            j++
        }
        return if (lineCount == doubleCount && lineCount * 5 == cards.size) {
            i - 1
        } else {
            -1
        }
    }
}
