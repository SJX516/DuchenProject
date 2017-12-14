package com.duchen.poker.model.type

import com.duchen.poker.model.TwoValue

import java.util.Arrays

class FourTakeOneTypeChecker : TypeChecker {

    override val type: CardGroupType
        get() = CardGroupType.FOUR_TAKE_ONE

    override val myPossibleCardCount: List<Int>
        get() = Arrays.asList(5)

    override fun getMaxCardIfIsMyType(cards: MutableList<Int>): Int {
        val cardCount1 = TwoValue(0, 0)
        val cardCount2 = TwoValue(0, 0)

        for (card in cards) {
            when {
                cardCount1.key == 0 -> {
                    cardCount1.key = card
                    cardCount1.value = 1
                }
                cardCount1.key == card -> cardCount1.value = cardCount1.value + 1
                cardCount2.key == 0 -> {
                    cardCount2.key = card
                    cardCount2.value = 1
                }
                cardCount2.key == card -> cardCount2.value = cardCount2.value + 1
                else -> return -1
            }
        }

        return when {
            cardCount1.value == 4 -> cardCount1.key
            cardCount2.value == 4 -> cardCount2.key
            else -> -1
        }
    }

}
