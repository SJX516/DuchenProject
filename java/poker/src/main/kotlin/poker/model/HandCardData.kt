package poker.model

import poker.CardLibrary

import java.util.ArrayList
import java.util.Arrays

class HandCardData {

    var handCardList: MutableList<Int> = ArrayList()
    var putCardGroup = CardGroup()
    var handCardValue = HandCardValue()
    var handCardGroupList: MutableList<CardGroup> = ArrayList()

    fun noMoreCard(): Boolean {
        return handCardList.isEmpty()
    }

    fun addCards(cards: List<Int>) {
        handCardList.addAll(cards)
    }

    fun setCardGroups(groups: MutableList<CardGroup>) {
        handCardGroupList = groups
    }

    @JvmOverloads
    fun putCards(cardGroup: CardGroup = putCardGroup) {
        val putCardList = cardGroup.cardList
        handCardList.removeAll(putCardList)
    }

    fun addCards(cardGroup: CardGroup) {
        val cardList = cardGroup.cardList
        handCardList.addAll(cardList)
    }

    override fun toString(): String {
        val arrays = IntArray(handCardList.size)
        for (i in arrays.indices) {
            arrays[i] = handCardList[i]
        }
        Arrays.sort(arrays)
        val sb = StringBuilder()
        sb.append("HandCardData{" + "handCardList=[")
        for (array in arrays) {
            sb.append(CardLibrary.getCardChar(array))
        }
        sb.append("]")
        sb.append(", handCardValue=$handCardValue, putCardGroup=$putCardGroup}")
        return sb.toString()
    }
}
