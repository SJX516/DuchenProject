package poker.model

import poker.CardLibrary
import poker.model.type.CardGroupType

import java.util.ArrayList

class CardGroup {

    var cardGroupType = CardGroupType.ERROR
    var cardList: MutableList<Int> = ArrayList()
    //该牌型中用于决定大小的值（比如33344中的3）
    var maxCard = -1
    var value = 0

    fun appendCardToSingleLine(card: Int): Boolean {
        if (cardGroupType === CardGroupType.SINGLE_LINE) {
            if (card == (maxCard + 1) || card == (maxCard - cardList.size - 1)) {
                cardList.add(card)
                maxCard = if (card > maxCard) card else maxCard
                return true
            }
        }
        return false
    }

    fun removeCardFromSingleLine(card: Int) {
        if (cardGroupType === CardGroupType.SINGLE_LINE) {
            cardList.remove(card)
            val nullableMax = cardList.max()
            if (nullableMax == null) {
                maxCard = -1
            } else {
                maxCard = nullableMax
            }
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append(cardGroupType).append(" ")
        for (card in cardList) {
            sb.append(CardLibrary.Companion.getCardChar(card))
        }
        sb.append("  maxCard: ").append(maxCard).append("  value: ").append(value)
        return sb.toString()
    }

}
