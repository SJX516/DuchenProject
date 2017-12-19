package poker.model.type

import java.util.Arrays

class BombTypeChecker : TypeChecker {

    override val type: CardGroupType
        get() = CardGroupType.BOMB_CARD

    override val myPossibleCardCount: List<Int>
        get() = Arrays.asList(4)

    override fun getMaxCardIfIsMyType(cards: MutableList<Int>): Int {
        val card = cards[0]
        return if (cards.any { it != card }) -1 else card
    }
}
