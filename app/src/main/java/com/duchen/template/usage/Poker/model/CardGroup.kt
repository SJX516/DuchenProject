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

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append(cardGroupType).append(" ")
        for (card in cardList.sorted()) {
            sb.append(CardLibrary.Companion.getCardChar(card))
        }
        sb.append("  maxCard: ").append(maxCard).append("  value: ").append(value)
        return sb.toString()
    }

}
