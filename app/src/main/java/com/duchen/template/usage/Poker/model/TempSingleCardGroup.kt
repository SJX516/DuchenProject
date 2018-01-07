package com.duchen.template.usage.Poker.model

import poker.model.CardGroup
import poker.model.type.CardGroupType

class TempSingleCardGroup {

    var isLineFromNone = true

    var cardGroup = CardGroup()
    var useThreeTypeCards = ArrayList<Int>()
    var useDoubleTypeCards = ArrayList<Int>()
    var helpSingleCards = ArrayList<Int>()

    var useThreeTypeCardsEXT = ArrayList<Int>()
    var useDoubleTypeCardsEXT = ArrayList<Int>()
    var helpSingleCardsEXT = ArrayList<Int>()
    var hasUseOtherTypeEXT = false

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

    fun release() : ArrayList<Int> {
        val list = ArrayList<Int>()
        list.addAll(useThreeTypeCards)
        list.addAll(useThreeTypeCardsEXT)
        list.addAll(useDoubleTypeCardsEXT)
        list.addAll(useDoubleTypeCards)
        list.addAll(helpSingleCardsEXT)
        list.addAll(helpSingleCards)
        return list
    }

}