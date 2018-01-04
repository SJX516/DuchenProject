package com.duchen.template.usage.Poker.model

import poker.model.CardGroup
import poker.model.type.CardGroupType

class TempSingleCardGroup {

    var cardGroup = CardGroup()
    var useThreeTypeCards = ArrayList<Int>()
    var useDoubleLineTypeCards = ArrayList<Int>()
    var alsoUsedByOtherGroupCards = ArrayList<Int>()
    var helpSingleCards = ArrayList<Int>()
    var hasUseOtherType = false

    fun appendCardToSingleLine(card: Int): Boolean {
        if (cardGroup.cardGroupType === CardGroupType.SINGLE_LINE) {
            if (card == (cardGroup.maxCard + 1) || card == (cardGroup.maxCard - cardGroup.cardList.size)) {
                cardGroup.cardList.add(card)
                cardGroup.maxCard = if (card > cardGroup.maxCard) card else cardGroup.maxCard
                return true
            }
        }
        return false
    }

    fun removeCardFromSingleLine(card: Int) {
        if (cardGroup.cardGroupType === CardGroupType.SINGLE_LINE) {
            cardGroup.cardList.remove(card)
            val nullableMax = cardGroup.cardList.max()
            if (nullableMax == null) {
                cardGroup.maxCard = -1
            } else {
                cardGroup.maxCard = nullableMax
            }
        }
    }

    fun canHelp() : Boolean {
        return helpSingleCards.size >= (useThreeTypeCards.size
                + useDoubleLineTypeCards.size - alsoUsedByOtherGroupCards.size)
    }

    fun initAlsoValue(other : TempSingleCardGroup) {
        for (two in other.useDoubleLineTypeCards) {
            if (!alsoUsedByOtherGroupCards.contains(two) && useDoubleLineTypeCards.contains(two)) {
                alsoUsedByOtherGroupCards.add(two)
            }
        }
        for (three in other.useThreeTypeCards) {
            if (!alsoUsedByOtherGroupCards.contains(three) && useThreeTypeCards.contains(three)) {
                alsoUsedByOtherGroupCards.add(three)
            }
        }
    }
}